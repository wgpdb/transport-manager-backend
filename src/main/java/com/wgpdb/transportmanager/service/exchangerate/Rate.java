package com.wgpdb.transportmanager.service.exchangerate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    @JsonProperty("effectiveDate")
    private LocalDate effectiveDate;

    @JsonProperty("mid")
    private Double mid;
}