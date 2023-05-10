package com.covid.covidall.service.integration.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.covid.covidall.exception.custom.NotFoundException;
import com.covid.covidall.jpa.model.Country;
import com.covid.covidall.service.CountryService;
import com.covid.covidall.service.integration.IntegrationTest;
import com.covid.covidall.service.integration.WiremockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CountryServiceTest extends IntegrationTest {

  @Autowired
  private CountryService countryService;

  private static final String ICELAND = "Iceland";
  private static final String ICELAND_CASE = "iCeland";
  private static final String ICELAN = "Icelan";
  private static final Country COUNTRY_ENTITY = new Country("IS", "Iceland");

  @Test
  void loadCountries_found() {

    WiremockUtils.stubExternalApiCountry();

    Country result = countryService.findCountry(ICELAND);
    Assertions.assertEquals(COUNTRY_ENTITY, result);
  }
  @Test
  void loadCountries_differentCaseFound() {

    WiremockUtils.stubExternalApiCountry();

    Country result = countryService.findCountry(ICELAND_CASE);
    Assertions.assertEquals(COUNTRY_ENTITY, result);
  }

  @Test
  void loadCountries_notFound() {

    WiremockUtils.stubExternalApiCountry();

    NotFoundException exception = assertThrows(NotFoundException.class, () ->
        countryService.findCountry(ICELAN));
    Assertions.assertEquals(exception.getMessage(), "Country: Icelan not found.");
  }

}
