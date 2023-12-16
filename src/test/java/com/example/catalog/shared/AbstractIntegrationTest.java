package com.example.catalog.shared;

import com.example.catalog.MainApplication;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("it")
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.config.location=src/test/resources/application-it.yml" })
public abstract class AbstractIntegrationTest {

  @Autowired
  protected MockMvc mockMvc;

  private static final MySQLContainer<?> mysqlContainer =
      new MySQLContainer<>("mysql:8.0")
          .withDatabaseName("testdb")
          .withUsername("testuser")
          .withPassword("testpassword");

  @BeforeAll
  static void setUp() {
    mysqlContainer.start();
    System.setProperty("spring.datasource.url", mysqlContainer.getJdbcUrl());
    System.setProperty("spring.datasource.username", mysqlContainer.getUsername());
    System.setProperty("spring.datasource.password", mysqlContainer.getPassword());
  }
}