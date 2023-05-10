package com.covid.covidall.jpa.repository;

import com.covid.covidall.jpa.model.Country;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

  Optional<Country> findFirstByNameIgnoreCase(String name);

}
