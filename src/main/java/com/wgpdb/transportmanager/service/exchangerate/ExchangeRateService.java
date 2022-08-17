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

    //todo: holiday handling on dateValidator method

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

    public Rate getRate(Currency currency, LocalDate eventDate) {
        LocalDate rateDate = dateValidator(eventDate);

        URI url = UriComponentsBuilder.fromHttpUrl(exchangeRateServiceConfig.getNbpApiUrl())
                .pathSegment(currency.getLabel())
                .pathSegment(rateDate.toString())
                .build()
                .encode()
                .toUri();

        try {
            return webClient
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
            return null;
        }
    }
}