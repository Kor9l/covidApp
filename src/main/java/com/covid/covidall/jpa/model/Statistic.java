package com.covid.covidall.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@Setter
public class Statistic implements Comparable<Statistic>{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private String countryCode;

  private Long cases;

  @Enumerated(EnumType.STRING)
  private CovidStatus status;

  private LocalDateTime date;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Statistic)) {
      return false;
    }
    Statistic statistic = (Statistic) o;
    return Objects.equals(countryCode, statistic.countryCode) && Objects.equals(
        cases, statistic.cases) && status == statistic.status && Objects.equals(date,
        statistic.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(countryCode, cases, status, date);
  }

  @Override
  public int compareTo(@NotNull Statistic statistic) {
    return (int) (cases - statistic.getCases());
  }
}
