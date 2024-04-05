package com.example.catalog.security.service;

import com.example.catalog.exception.UnauthenticationException;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.repository.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


/**
 * Handles loading of logged user information.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * Loads currently logged user.
   *
   * @param login String, user login
   * @return UserDetailsImpl with user information
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String login) {
    User user = userRepository.findByLogin(login)
        .orElseThrow(UnauthenticationException::new);

    return new UserDetailsImpl(user);
  }
}

