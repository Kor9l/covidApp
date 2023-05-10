package com.covid.covidall.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "covid19api")
@Getter
@Setter
public class CovidExternalApiConfig {
    String url;
}
