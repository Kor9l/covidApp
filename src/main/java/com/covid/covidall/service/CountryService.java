package com.covid.covidall.service;

import com.covid.covidall.exception.custom.NotFoundException;
import com.covid.covidall.jpa.model.Country;
import com.covid.covidall.jpa.repository.CountryRepository;
import com.covid.covidall.service.client.ExternalCovidApiClient;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {

  private final ExternalCovidApiClient client;
  private final CountryRepository countryRepository;

  public Country findCountry(String country) {
    Optional<Country> optionalCountry = countryRepository.findFirstByNameIgnoreCase(country);
    if (optionalCountry.isPresent()) {
      return optionalCountry.get();
    } else {
      return populateCountries(country);
    }
  }

  private Country populateCountries(String name) {
    if (countryRepository.count() < 248) {
      countryRepository.saveAll(client.getCountries());
    }
    return countryRepository.findFirstByNameIgnoreCase(name)
        .orElseThrow(() -> new NotFoundException("Country: " + name + " not found."));
  }
}
