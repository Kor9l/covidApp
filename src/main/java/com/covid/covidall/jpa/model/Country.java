package com.covid.covidall.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {

  @Id
  private String countryCode;
  private String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Country)) {
      return false;
    }
    Country country = (Country) o;
    return Objects.equals(countryCode, country.countryCode) && Objects.equals(
        name, country.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(countryCode, name);
  }
}
