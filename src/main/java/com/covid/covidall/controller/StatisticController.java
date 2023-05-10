package com.covid.covidall.controller;

import com.covid.covidall.dto.CountryMinMaxResponseDTO;
import com.covid.covidall.service.CovidStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class StatisticController {

  private final CovidStatisticService covidStatisticService;

  @Operation(summary = "Get statistic by country")
  @GetMapping
  public CountryMinMaxResponseDTO getCountryStatistic(
      @Parameter(description = "Country name", example = "Belarus")
      @RequestParam String country,
      @Parameter(description = "Date from", example = "2022-01-01T00:00:00")
      @RequestParam String from,
      @Parameter(description = "Date to", example = "2022-03-01T00:00:00")
      @RequestParam String to
  ) {

    return covidStatisticService.statisticPage(country, LocalDateTime.parse(from),
        LocalDateTime.parse(to));

  }
}
