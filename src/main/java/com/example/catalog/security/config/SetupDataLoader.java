package com.example.catalog.security.config;

import com.example.catalog.date.service.DateService;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.enums.UserRoleEnum;
import com.example.catalog.user.enums.UserStatusEnum;
import com.example.catalog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Creates necessary roles and user for correct
 * functionality of application.
 */
@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
  private boolean alreadySetup = false;

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final Environment environment;
  private final DateService dateService;

  /**
   * On startup of the check if roles and user exists.
   * If not creates them.
   *
   * @param event ContextRefreshedEvent
   */
  @Override
  @Transactional
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    if (alreadySetup) {

      return;
    }

    // Create users
    createUserIfNotFound(
          environment.getProperty("defaultCredentials.admin.login"),
          environment.getProperty("defaultCredentials.admin.password"),
          UserRoleEnum.ADMIN
    );
    createUserIfNotFound(
          environment.getProperty("defaultCredentials.user.login"),
          environment.getProperty("defaultCredentials.user.password"),
          UserRoleEnum.USER
    );

    alreadySetup = true;
  }

  /**
   * Creates user with given login if it doesn't exist.
   *
   * @param login String, login of User
   * @param role Role, role of user
   * @return User with given email
   */
  private User createUserIfNotFound(
      final String login,
      final String password,
      final UserRoleEnum role
  ) {
    if (!userService.checkIfExistsByLogin(login)) {
      User user = User.builder()
          .login(login)
          .password(passwordEncoder.encode(password))
          .status(UserStatusEnum.ENABLED)
          .role(role)
          .createDate(dateService.getCurrentDate())
          .updateDate(dateService.getCurrentDate())
          .build();
      user = userService.save(user);
      return user;
    }

    return userService.getUserByLogin(login);
  }
}
