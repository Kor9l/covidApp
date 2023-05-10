package com.covid.covidall.jpa.repository;

import com.covid.covidall.jpa.model.StatisticUpdate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticUpdateRepository  extends JpaRepository<StatisticUpdate, String> {
}
