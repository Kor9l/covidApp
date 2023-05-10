package com.covid.covidall.jpa.repository;

import com.covid.covidall.jpa.model.Statistic;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    Optional<Statistic> findFirstByCountryCodeAndDateBetweenOrderByCasesAsc(String countryCode, LocalDateTime
        before, LocalDateTime after);
    Optional<Statistic> findFirstByCountryCodeAndDateBetweenOrderByCasesDesc(String countryCode, LocalDateTime
        before, LocalDateTime after);
}
