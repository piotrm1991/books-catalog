package com.example.catalog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class that initializes and runs the catalog application.
 */
@Slf4j
@SpringBootApplication
public class MainApplication {

  public static void main(String[] args) {
    log.info("The application is running");
    SpringApplication.run(MainApplication.class, args);
  }
}
