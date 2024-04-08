package com.example.catalog.integration.security;

import static com.example.catalog.util.MessagesConstants.SuccessfulLoginMessage;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.shared.RestPageImpl;
import com.example.catalog.user.UserHelper;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.mapper.UserMapper;
import com.example.catalog.user.repository.UserRepository;
import com.example.catalog.user.response.UserResponse;
import com.example.catalog.user.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationTest extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private Environment environment;

  private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  private User userAdmin;
  private User userUser;

  @BeforeEach
  void prepareNeededUsers() {
    userAdmin = userService.getUserByLogin(environment.getProperty("defaultCredentials.admin.login"));
    userUser = userService.getUserByLogin(environment.getProperty("defaultCredentials.user.login"));
    mapper.findAndRegisterModules();
  }

  @Test
  @Transactional
  public void givenCorrectCredentials_whenLogin_thenCorrect() throws Exception {

    var responseLogin = mockMvc.perform(post(SecurityHelper.urlPathLogin)
                .with(httpBasic(userAdmin.getLogin(), environment.getProperty("defaultCredentials.admin.password")))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andReturn();

    String responseMessage = responseLogin.getResponse().getContentAsString();
    assertEquals(responseMessage, SuccessfulLoginMessage);

    MockHttpSession session = (MockHttpSession) responseLogin.getRequest().getSession();

    Page<User> expectedUsers = userRepository
          .findAll(PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "id")));
    List<UserResponse> expectedUsersResponse = new ArrayList<>();
    expectedUsers.getContent().forEach(u -> expectedUsersResponse.add(userMapper.mapEntityToResponse(u)));

    var response = mockMvc.perform(get(UserHelper.userUrlPath)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andReturn();

    Page<UserResponse> usersResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<UserResponse>>() {});

    assertFalse(usersResponse.isEmpty());
    assertEquals(usersResponse.getTotalElements(), expectedUsers.getTotalElements());
    assertEquals(usersResponse.getTotalPages(), expectedUsers.getTotalPages());
    assertEquals(usersResponse.getContent(), expectedUsersResponse);
  }

  @Test
  @Transactional
  public void givenIncorrectCredentials_whenLogin_thenException() throws Exception {
    mockMvc.perform(get(SecurityHelper.urlPathLogin)
                .with(httpBasic("adminBad", "addfdfmin"))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isUnauthorized())
          .andReturn();
  }

  @Test
  @Transactional
  public void givenCorrectCredentials_whenLoginAndLogout_thenCorrect() throws Exception {

    var responseLogin = mockMvc.perform(post(SecurityHelper.urlPathLogin)
                .with(httpBasic(userAdmin.getLogin(), environment.getProperty("defaultCredentials.admin.password")))
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andReturn();

    String responseMessage = responseLogin.getResponse().getContentAsString();
    assertEquals(responseMessage, SuccessfulLoginMessage);

    MockHttpSession session = (MockHttpSession) responseLogin.getRequest().getSession();

    mockMvc.perform(get(UserHelper.userUrlPath)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());

    mockMvc.perform(delete(SecurityHelper.urlPathLogout)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());

    mockMvc.perform(get(UserHelper.userUrlPath)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isUnauthorized());
  }
}
