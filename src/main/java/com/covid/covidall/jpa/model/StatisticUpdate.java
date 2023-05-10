package com.covid.covidall.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticUpdate {

  @Id
  String countryCode;
  LocalDateTime lastUpdate;
  LocalDateTime lastDate;
  Long previousMaxCases;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StatisticUpdate)) {
      return false;
    }
    StatisticUpdate that = (StatisticUpdate) o;
    return Objects.equals(countryCode, that.countryCode) && Objects.equals(
        lastUpdate, that.lastUpdate) && Objects.equals(lastDate, that.lastDate)
        && Objects.equals(previousMaxCases, that.previousMaxCases);
  }

  @Override
  public int hashCode() {
    return Objects.hash(countryCode, lastUpdate, lastDate, previousMaxCases);
  }
}
