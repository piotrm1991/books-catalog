package com.example.catalog.security.controller;

import static com.example.catalog.util.MessagesConstants.SuccessfulLoginMessage;

import javax.servlet.http.HttpSession;

import com.example.catalog.security.service.UserDetailsImpl;
import com.example.catalog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
    log.info("POST-request: successful login with login: {}", currentUserLogin);

    session.setMaxInactiveInterval(60 * 5);

    return SuccessfulLoginMessage;
  }

  /**
   * Method returns role of the currently logged-in user.
   *
   * @return String role name.
   */
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/currentUserRole")
  public String getCurrentUserRole() {
    String currentUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
    String currentUserRole = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCurrentUserRole();
    log.info("GET-request: getting current role: {} from user: {}", currentUserRole, currentUserLogin);

    return currentUserRole;
  }
}
