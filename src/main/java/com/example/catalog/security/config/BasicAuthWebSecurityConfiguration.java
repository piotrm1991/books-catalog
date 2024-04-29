package com.example.catalog.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * BasicAuthWebSecurityConfiguration contains configuration of
 * authentication and authorization of application.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
@Slf4j
public class BasicAuthWebSecurityConfiguration implements WebMvcConfigurer {

  private final UserDetailsService userDetailsService;

  /**
   * Method creates LogoutHandler. Just for logs to show that user was actually logged out.
   *
   * @return LogoutHandler.
   */
  LogoutHandler logoutHandler() {
    return ((request, response, authentication) -> {
      if (authentication != null) {
        String username = authentication.getName();
        log.info("DELETE-request: successful logout of user: {}.", username);
      } else {
        log.info("DELETE-request: tried to logout on finished session");
      }
    });
  }

  /**
   * Method sets up HttpSecurity object with configuration for authorization and authentication.
   *
   * @param http HttpSecurity object, holds configuration for http requests
   * @return http HttpSecurity object, holds configuration for http requests
   * @throws Exception when fails to build http object
   */
  @Bean
  protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

    http
        .csrf().disable()
        .cors().and()
        .authorizeRequests()
        .anyRequest().authenticated()
        .and().userDetailsService(userDetailsService())
        .logout().deleteCookies("JSESSIONID").addLogoutHandler(logoutHandler())
        .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT)))
        .and()
        .httpBasic((basic) -> basic
              .addObjectPostProcessor(new ObjectPostProcessor<BasicAuthenticationFilter>() {
                @Override
                public <O extends BasicAuthenticationFilter> O postProcess(O filter) {
                  filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
                  return filter;
                }
              })
    );

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

  /**
   * Method declares cors origin.
   * Sets up url of outside application that has access to this app.
   *
   * @param registry CorsRegistry.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
          .allowedOrigins("http://localhost:4200")
          .allowedMethods("GET", "POST", "PUT", "DELETE")
          .allowCredentials(true);
  }
}
