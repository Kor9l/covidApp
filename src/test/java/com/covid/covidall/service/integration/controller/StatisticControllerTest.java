package com.covid.covidall.service.integration.controller;

import com.covid.covidall.controller.StatisticController;
import com.covid.covidall.dto.CountryMinMaxResponseDTO;
import com.covid.covidall.service.integration.IntegrationTest;
import com.covid.covidall.service.integration.WiremockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class StatisticControllerTest extends IntegrationTest {

  @Autowired
  private StatisticController statisticController;

  public static final String BELARUS = "Belarus";
  private static final CountryMinMaxResponseDTO RESPONSE_DTO = CountryMinMaxResponseDTO.builder()
      .minCases(0L)
      .maxCases(8921L)
      .country(BELARUS)
      .build();

  @Test
  void getStatistic() {
    WiremockUtils.stubExternalApiCountry();
    WiremockUtils.stubExternalApiFirst();

    CountryMinMaxResponseDTO result = statisticController.getCountryStatistic(BELARUS,
        "2020-03-01T00:00:00", "2022-03-01T00:00:00");
    Assertions.assertEquals(RESPONSE_DTO, result);
  }

  @Test
  void getStatistic_notFound() {
    WiremockUtils.stubExternalApiCountry();
    WiremockUtils.stubExternalApiFirst();

    CountryMinMaxResponseDTO result = statisticController.getCountryStatistic(BELARUS,
        "2020-03-01T00:00:00", "2022-03-01T00:00:00");
    Assertions.assertEquals(RESPONSE_DTO, result);
  }
}