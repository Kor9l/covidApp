package com.covid.covidall.service;

import com.covid.covidall.dto.CountryMinMaxResponseDTO;
import com.covid.covidall.exception.custom.NotFoundException;
import com.covid.covidall.jpa.model.Country;
import com.covid.covidall.jpa.repository.StatisticRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CovidStatisticService {

  private final StatisticRepository statisticRepository;
  private final CacheableService cacheableService;
  private final CountryService countryService;
  public CountryMinMaxResponseDTO statisticPage(String countryName, LocalDateTime from,
      LocalDateTime to) {
    Country country = countryService.findCountry(countryName);
    var lastDate = cacheableService.checkForUpdates(country);
    var minPerPeriod = statisticRepository.findFirstByCountryCodeAndDateBetweenOrderByCasesAsc(
        country.getCountryCode(), from, to).orElseThrow(()-> new NotFoundException("Statistic for country: "+ countryName + " not found."));
    var maxFromPeriod = statisticRepository.findFirstByCountryCodeAndDateBetweenOrderByCasesDesc(
        country.getCountryCode(), from, to).orElseThrow(()-> new NotFoundException("Statistic for country: "+ countryName + " not found."));
    return CountryMinMaxResponseDTO.builder()
        .country(country.getName())
        .minCases(minPerPeriod.getCases())
        .maxCases(maxFromPeriod.getCases())
        .build();
  }
}
