package com.wgpdb.transportmanager.service.weather.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("country")
    public String country;

    @JsonProperty("sunrise")
    public Integer sunrise;

    @JsonProperty("sunset")
    public Integer sunset;
}