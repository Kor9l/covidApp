package com.covid.covidall.service.integration.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class StorageInitializer implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final String IMAGE = "postgres:13";
  private static final String TEST_DATABASE = "Covid";
  private static final String USER_NAME = "dbuser";
  private static final String PASSWORD = "dbpass";
  private static final String ISOLATION_LEVEL_OPTION = "default_transaction_isolation=repeatable read";
  private final PostgreSQLContainer postgresContainer =
      new PostgreSQLContainer<>(DockerImageName.parse(IMAGE).asCompatibleSubstituteFor("postgres"))
          .withReuse(true)
          .withDatabaseName(TEST_DATABASE)
          .withUsername(USER_NAME)
          .withPassword(PASSWORD)
          .withCommand("-c", ISOLATION_LEVEL_OPTION);
  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
      postgresContainer.start();

    TestPropertyValues.of("spring.datasource.url=" + postgresContainer.getJdbcUrl())
        .applyTo(applicationContext.getEnvironment());
  }
}
