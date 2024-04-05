package com.example.catalog.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 * BasicAuthWebSecurityConfiguration contains configuration of
 * authentication and authorization of application.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class BasicAuthWebSecurityConfiguration {

  private final UserDetailsService userDetailsService;

  /**
   * Method sets up HttpSecurity object with configuration for authorization and authentication.
   *
   * @param http HttpSecurity object, holds configuration for http requests
   * @return http HttpSecurity object, holds configuration for http requests
   * @throws Exception when fails to build http object
   */
  @Bean
  protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

    http.csrf().disable()
        .authorizeRequests()
        .anyRequest().authenticated()
        .and().userDetailsService(userDetailsService())
        .logout(logout -> logout.deleteCookies("JSESSIONID"))
        .httpBasic((basic) -> basic
            .addObjectPostProcessor(new ObjectPostProcessor<BasicAuthenticationFilter>() {
              @Override
              public <O extends BasicAuthenticationFilter> O postProcess(O filter) {
                filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
                return filter;
              }
            })
        ).sessionManagement().maximumSessions(1);

    return http.build();
  }

  /**
   * Prepares UserDetailsService.
   *
   * @return UserDetailsService object
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return userDetailsService;
  }

  /**
   * Prepares PasswordEncoder object.
   *
   * @return PasswordEncoder object.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }
}
