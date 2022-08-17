package com.wgpdb.transportmanager.service.exchangerate;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExchangeRateServiceConfig {

    @Value("${nbp.api.url}")
    private String NbpApiUrl;
}