package com.covid.covidall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerService {

  private final CacheableService cacheableService;

  @Scheduled(cron = "0 0 1 * * ?")
  public void resetCacheDaily() {
    cacheableService.evictCache();
  }
}
