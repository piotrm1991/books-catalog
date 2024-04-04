package com.example.catalog.date.service;

import java.time.LocalDate;

/**
 * Service interface defining methods for working with dates.
 */
public interface DateService {

  /**
   * Gets current date as LocalDate.
   *
   * @return The current date in defined format as LocalDate.
   */
  LocalDate getCurrentDate();
}
