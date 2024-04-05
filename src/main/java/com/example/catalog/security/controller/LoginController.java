package com.example.catalog.security.controller;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for login action.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

  /**
   * Method returns message on user successful login.
   * Method sets session time limit for 5 minutes.
   *
   * @return String message on successful login
   */
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/login")
  public String login(HttpSession session) {
    String currentUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("GET-request: successful login with login: {}", currentUserLogin);

    session.setMaxInactiveInterval(60 * 5);

    return "You successfully logged in!";
  }
}
