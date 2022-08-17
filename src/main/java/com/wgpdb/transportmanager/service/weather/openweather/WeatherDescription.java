package com.wgpdb.transportmanager.service.weather.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDescription {

    @JsonProperty("main")
    public String main;

    @JsonProperty("description")
    public String description;

    @JsonProperty("icon")
    public String icon;
}