package com.covid.covidall.service;

import com.covid.covidall.dto.CountryStatisticResponseDTO;
import com.covid.covidall.jpa.model.Country;
import com.covid.covidall.jpa.model.Statistic;
import com.covid.covidall.jpa.model.StatisticUpdate;
import com.covid.covidall.jpa.repository.StatisticRepository;
import com.covid.covidall.jpa.repository.StatisticUpdateRepository;
import com.covid.covidall.mapper.ResponseMapper;
import com.covid.covidall.service.client.ExternalCovidApiClient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateService {

  private final ExternalCovidApiClient client;
  private final StatisticUpdateRepository updateRepository;
  private final StatisticRepository statisticRepository;

  public LocalDateTime checkUpdates(Country country) {
    Optional<StatisticUpdate> byCountryCode = updateRepository.findById(
        country.getCountryCode());
    if (byCountryCode.isPresent()) {
      StatisticUpdate statisticUpdate = byCountryCode.get();
      LocalDateTime lastUpdate = statisticUpdate.getLastUpdate();
      if (lastUpdate.toLocalDate().isEqual(LocalDate.now())) {
        return statisticUpdate.getLastDate();
      } else {
        return makeUpdate(country, statisticUpdate).getLastDate();
      }
    } else {
      return initNewCountry(country).getLastDate();
    }

  }

  private StatisticUpdate initNewCountry(Country country) {
    List<Statistic> statisticList = mapStatisticResponse(client.getStatisticForCountry(country));
    Pair<List<Statistic>, Long> statisticWithMaxCases = calculateDailyCases(statisticList, 0L);
    statisticRepository.saveAll(statisticWithMaxCases.a);
    LocalDateTime lastDate = getLastDate(statisticList, LocalDateTime.now());
    return saveUpdate(StatisticUpdate.builder()
        .lastUpdate(LocalDateTime.now())
        .lastDate(lastDate)
        .previousMaxCases(statisticWithMaxCases.b)
        .countryCode(country.getCountryCode())
        .build());
  }

  private StatisticUpdate makeUpdate(Country country, StatisticUpdate lastUpdate) {
    var statisticList = mapStatisticResponse(
        client.getStatisticForPeriod(country, lastUpdate.getLastDate().plusDays(1),
            LocalDateTime.now()));
    Pair<List<Statistic>, Long> listLongPair = calculateDailyCases(statisticList,
        lastUpdate.getPreviousMaxCases());
    statisticRepository.saveAll(listLongPair.a);

    lastUpdate.setLastUpdate(LocalDateTime.now());
    LocalDateTime lastDate = getLastDate(statisticList, lastUpdate.getLastDate());
    lastUpdate.setLastDate(lastDate);
    lastUpdate.setPreviousMaxCases(listLongPair.b);

    return saveUpdate(lastUpdate);
  }

  private LocalDateTime getLastDate(List<Statistic> statisticList, LocalDateTime lastDate) {
    return statisticList.stream()
        .map(Statistic::getDate)
        .max(LocalDateTime::compareTo)
        .orElse(lastDate);
  }


  public StatisticUpdate saveUpdate(StatisticUpdate update) {
    return updateRepository.save(update);
  }

  private List<Statistic> mapStatisticResponse(CountryStatisticResponseDTO[] responseDTOS) {
    return Arrays.stream(responseDTOS)
        .map(ResponseMapper.INSTANCE::mapFromDTO)
        .toList();
  }

  private Pair<List<Statistic>, Long> calculateDailyCases(List<Statistic> statistics,
      Long startCases) {
    AtomicLong atomicLong = new AtomicLong(startCases);

    List<Statistic> sorted = statistics.stream()
        .sorted()
        .map(it -> {
          it.setCases(it.getCases() - atomicLong.getAndSet(it.getCases()));
          return it;
        })
        .toList();
    return new Pair<>(sorted, atomicLong.get());
  }
}
