package com.wgpdb.transportmanager.service.weather;

import com.wgpdb.transportmanager.domain.WeatherData;
import com.wgpdb.transportmanager.exception.WeatherDataCouldNotBeRetrievedException;
import com.wgpdb.transportmanager.service.weather.openweather.OpenWeatherApiResponse;
import com.wgpdb.transportmanager.service.weather.openweather.ResponseList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherServiceConfig weatherServiceConfig;
    private final WebClient webClient = WebClient.create();

    private URI url(String city) {
        return UriComponentsBuilder.fromHttpUrl(weatherServiceConfig.getOpenWeatherApiUrl())
                .queryParam("q", city)
                .queryParam("units", "metric")
                .queryParam("appid", weatherServiceConfig.getOpenWeatherApiKey())
                .build()
                .encode()
                .toUri();
    }

    private Optional<OpenWeatherApiResponse> getWeatherApiCall(String city) {
        try {
            return Optional.ofNullable(Objects.requireNonNull(webClient
                            .get()
                            .uri(url(city)))
                    .retrieve()
                    .bodyToMono(OpenWeatherApiResponse.class)
                    .block());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public LocalDateTime convertFromString(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }

    public List<WeatherData> getWeatherForecastByDayList(LocalDate date, String city)
            throws WeatherDataCouldNotBeRetrievedException {

        OpenWeatherApiResponse response = getWeatherApiCall(city)
                .orElseThrow(WeatherDataCouldNotBeRetrievedException::new);

        List<ResponseList> responseList = response.getResponseList();

        List<WeatherData> weatherDataList = new ArrayList<>();

        for (ResponseList responseLists : responseList) {
            if (responseLists.getForecastDateTime().contains(date.toString())) {
                WeatherData weatherData = WeatherData.builder()
                        .city(response.getCity().getName())
                        .country(response.getCity().getCountry())
                        .dateTime(convertFromString(responseLists.getForecastDateTime()))
                        .averageTemp(responseLists.getWeatherParams().getTemp())
                        .feelsLikeTemp(responseLists.getWeatherParams().getFeelsLike())
                        .description(responseLists.getWeatherDescriptionList().get(0).getDescription())
                        .build();

                weatherDataList.add(weatherData);
            }
        }
        return weatherDataList;
    }
}