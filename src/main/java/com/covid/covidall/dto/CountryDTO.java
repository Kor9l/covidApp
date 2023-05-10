package com.covid.covidall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryDTO {
  @JsonProperty("Country")
  public String country;
  @JsonProperty("Slug")
  public String slug;
  @JsonProperty("ISO2")
  public String iSO2;
}
