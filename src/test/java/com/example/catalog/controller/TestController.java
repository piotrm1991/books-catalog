package com.example.catalog.controller;

import com.example.catalog.exception.AccessDeniedException;
import com.example.catalog.exception.BadRequestException;
import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.exception.UnauthenticationException;
import com.example.catalog.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for testing exception scenarios.
 */
@Slf4j
@RequestMapping("/test")
@ActiveProfiles("it")
@RestController
public class TestController {

  /**
   * Simulates throwing various exceptions based on the provided exception identifier.
   *
   * @param exceptionType The exception identifier to determine which exception to throw.
   * @throws Exception Depending on the given exception identifier.
   */
  @GetMapping("/exception/{exception_id}")
  public void getSpecificException(@PathVariable("exception_id") String exceptionType)
      throws Exception {
    if ("accessDenied".equals(exceptionType)) {

      throw new AccessDeniedException();
    } else if ("badRequest".equals(exceptionType)) {

      throw new BadRequestException("bad arguments");
    } else if ("entityNotFound".equals(exceptionType)) {

      throw new EntityNotFoundException("Entity not found.");
    } else if ("unauthentication".equals(exceptionType)) {

      throw new UnauthenticationException();
    } else if ("validation".equals(exceptionType)) {

      throw new ValidationException("You didn't pass validation.");
    } else {

      throw new RuntimeException("internal error");
    }
  }
}
