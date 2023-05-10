package com.covid.covidall.dto;

import java.util.Objects;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryMinMaxResponseDTO {

   private String country;

   private Long minCases;

   private Long maxCases;

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (!(o instanceof CountryMinMaxResponseDTO)) {
         return false;
      }
      CountryMinMaxResponseDTO that = (CountryMinMaxResponseDTO) o;
      return Objects.equals(country, that.country) && Objects.equals(minCases,
          that.minCases) && Objects.equals(maxCases, that.maxCases);
   }

   @Override
   public int hashCode() {
      return Objects.hash(country, minCases, maxCases);
   }
}
