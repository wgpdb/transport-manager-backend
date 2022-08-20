package com.wgpdb.transportmanager.service.exchangerate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateServiceConfig exchangeRateServiceConfig;
    private final WebClient webClient = WebClient.create();

    public LocalDate dateValidator(LocalDate date) {
        LocalDate validDate = null;
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            validDate = date.minusDays(1);
        } else if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            validDate = date.minusDays(2);
        } else if (date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            validDate = date.minusDays(3);
        } else {
            validDate = date.minusDays(1);
        }
        return validDate;
    }

    private URI requestUri(Currency currency, LocalDate rateDate) {
        return UriComponentsBuilder.fromHttpUrl(exchangeRateServiceConfig.getNbpApiUrl())
                .pathSegment(currency.getLabel())
                .pathSegment(rateDate.toString())
                .build()
                .encode()
                .toUri();
    }

    public Rate retrieveRate(Currency currency, LocalDate eventDate) {
        Rate rate;
        LocalDate rateDate = dateValidator(eventDate);

        while(true) {
            URI url = requestUri(currency, rateDate);

            try {
                rate = webClient
                        .get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(NbpApiResponse.class)
                        .block()
                        .getRates()
                        .stream()
                        .findFirst()
                        .get();
            } catch (Exception e) {
                log.error(e.getMessage());
                rate = null;
            }

            if (rate != null) {
                return rate;
            } else {
                rateDate = dateValidator(rateDate);
            }
        }
    }
}