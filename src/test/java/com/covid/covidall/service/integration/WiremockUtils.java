package com.covid.covidall.service.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@UtilityClass
public class WiremockUtils {

  private static final String COUNTRIES_PATH = "countries.json";
  private static final String BELARUS_FIRST_PATH = "firstBel.json";
  private static final String SAUTH_AFRICA_PERIOD = "period-za.json";

  public static StubMapping stubExternalApiFirst() {
    return stubFor(
        WireMock.get(urlPathMatching("/country/Belarus/status/confirmed"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile(BELARUS_FIRST_PATH))
    );
  }

  public static StubMapping stubExternalApiCountry() {
    return stubFor(
        WireMock.get(urlPathMatching("/countries"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile(COUNTRIES_PATH))
    );
  }
  public static StubMapping stubExternalApi() {
    return stubFor(
        WireMock.get(urlPathMatching("/country/south-africa/status/confirmed?from=2020-03-01'T'00:00:00&to=2022-03-01'T'00:00:00"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile(COUNTRIES_PATH))
    );
  }
}
