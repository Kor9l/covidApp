package com.covid.covidall.service.integration;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.covid.covidall.service.integration.config.StorageInitializer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.server.EnableStubRunnerServer;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ComponentScan({"com.covid.covidall"})
@EnableStubRunnerServer
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(
    initializers = {StorageInitializer.class})
public class IntegrationTest {

  private static final String HOST = "localhost";
  private static final int PORT = 8088;
  private static WireMockServer wireMockServer = new WireMockServer(PORT);

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(
      wireMockConfig().dynamicPort().dynamicHttpsPort());

  @BeforeAll
  static void init() {
    wireMockServer.start();
    WireMock.configureFor(HOST, PORT);
  }
}
