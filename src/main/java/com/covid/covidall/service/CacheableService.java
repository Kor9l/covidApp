package com.covid.covidall.service;

import static com.covid.covidall.conf.CachingConfig.COUNTRY_UPDATE;

import com.covid.covidall.jpa.model.Country;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheableService {

  private static final Logger log = LoggerFactory.getLogger(
      CacheableService.class);

  private final UpdateService updateService;

  @Cacheable(value = COUNTRY_UPDATE, key = "#country.countryCode")
  public LocalDateTime checkForUpdates(Country country) {
    log.info("Out of cash, check for updates.");
    return updateService.checkUpdates(country);
  }

  @CacheEvict(value = COUNTRY_UPDATE, allEntries = true)
  public void evictCache() {
    log.info("Daily update cache evicted!");
  }
}
