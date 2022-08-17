package com.wgpdb.transportmanager.service.weather.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherParams {

    @JsonProperty("temp")
    public Double temp;

    @JsonProperty("feels_like")
    public Double feelsLike;

    @JsonProperty("temp_min")
    public Double tempMin;

    @JsonProperty("temp_max")
    public Double tempMax;
}