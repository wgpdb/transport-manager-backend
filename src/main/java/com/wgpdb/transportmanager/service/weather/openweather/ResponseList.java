package com.wgpdb.transportmanager.service.weather.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseList {

    @JsonProperty("main")
    public WeatherParams weatherParams;

    @JsonProperty("weather")
    public List<WeatherDescription> weatherDescriptionList;

    @JsonProperty("dt_txt")
    public String forecastDateTime;
}