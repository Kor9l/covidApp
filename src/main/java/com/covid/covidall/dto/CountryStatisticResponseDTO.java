package com.covid.covidall.dto;

import com.covid.covidall.jpa.model.CovidStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class CountryStatisticResponseDTO {
  @JsonProperty("Country")
  public String country;
  @JsonProperty("CountryCode")
  public String countryCode;
  @JsonProperty("Province")
  public String province;
  @JsonProperty("City")
  public String city;
  @JsonProperty("CityCode")
  public String cityCode;
  @JsonProperty("Lat")
  public String lat;
  @JsonProperty("Lon")
  public String lon;
  @JsonProperty("Cases")
  public int cases;
  @JsonProperty("Status")
  public String status;
  @JsonProperty("Date")
  public Date date;

}