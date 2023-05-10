package com.covid.covidall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@ConfigurationPropertiesScan("com.covid.covidall.conf")
@SpringBootApplication
public class CovidApplication {

  public static void main(String[] args) {
    SpringApplication.run(CovidApplication.class, args);

  }
}
