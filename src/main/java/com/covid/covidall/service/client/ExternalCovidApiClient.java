package com.covid.covidall.service.client;

import com.covid.covidall.conf.CovidExternalApiConfig;
import com.covid.covidall.dto.CountryDTO;
import com.covid.covidall.dto.CountryStatisticResponseDTO;
import com.covid.covidall.jpa.model.Country;
import com.covid.covidall.mapper.ResponseMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ExternalCovidApiClient {

  private final RestTemplate restTemplate;
  private final CovidExternalApiConfig config;
  private static final String COUNTRY_PATH = "country";
  private static final String COUNTRIES_PATH = "countries";
  private static final String STATUS_PATH = "status";
  private static final String CONFIRMED_PATH = "confirmed";
  private static final String PARAM_FROM = "from";
  private static final String PARAM_TO = "to";

  private static final Logger log = LoggerFactory.getLogger(
      ExternalCovidApiClient.class);

  public CountryStatisticResponseDTO[] getStatisticForPeriod(Country country, LocalDateTime from,
      LocalDateTime to) {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(config.getUrl())
        .pathSegment(COUNTRY_PATH)
        .pathSegment(country.getName())
        .pathSegment(STATUS_PATH)
        .pathSegment(CONFIRMED_PATH)
        .queryParam(PARAM_FROM, from.toString())
        .queryParam(PARAM_TO, to.toString());
    log.info("Getting statistic for country: " + country.getName() );
    return restTemplate.getForEntity(uriBuilder.toUriString(),
        CountryStatisticResponseDTO[].class).getBody();
  }

  public CountryStatisticResponseDTO[] getStatisticForCountry(Country country) {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(config.getUrl())
        .pathSegment(COUNTRY_PATH)
        .pathSegment(country.getName())
        .pathSegment(STATUS_PATH)
        .pathSegment(CONFIRMED_PATH);
    log.info("Getting statistic for country: " + country.getName() );

    return restTemplate.getForEntity(uriBuilder.toUriString(),
        CountryStatisticResponseDTO[].class).getBody();
  }

  public List<Country> getCountries() {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(config.getUrl())
        .pathSegment(COUNTRIES_PATH);
    log.info("Getting country list");
    return Arrays.stream(restTemplate.getForEntity(uriBuilder.toUriString(),
        CountryDTO[].class).getBody())
        .map(ResponseMapper.INSTANCE::mapFromDTO)
        .toList();
  }


}
