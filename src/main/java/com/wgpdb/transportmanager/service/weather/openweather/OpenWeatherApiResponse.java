package com.wgpdb.transportmanager.service.weather.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherApiResponse {

    @JsonProperty("list")
    public List<ResponseList> responseList;

    @JsonProperty("city")
    public City city;
}