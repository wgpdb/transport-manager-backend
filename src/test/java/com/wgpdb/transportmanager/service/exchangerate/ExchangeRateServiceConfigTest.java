package com.wgpdb.transportmanager.service.exchangerate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeRateServiceConfigTest {

    @Autowired
    private ExchangeRateServiceConfig exchangeRateServiceConfig;

    @Test
    void getNbpApiUrlTest() {
        //Given & When
        String nbpApiUrl = exchangeRateServiceConfig.getNbpApiUrl();

        //When
        assertNotNull(nbpApiUrl);
    }
}