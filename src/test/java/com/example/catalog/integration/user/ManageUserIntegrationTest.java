package com.example.catalog.integration.user;

import static com.example.catalog.util.ErrorMessagesConstants.LoginAlreadyExists;
import static com.example.catalog.util.ErrorMessagesConstants.LoginIsRequiredMessage;
import static com.example.catalog.util.ErrorMessagesConstants.createEntityNotExistsMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;
import java.util.List;
import com.example.catalog.user.UserHelper;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.enums.UserStatusEnum;
import com.example.catalog.user.mapper.UserMapper;
import com.example.catalog.user.repository.UserRepository;
import com.example.catalog.user.response.UserResponse;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ManageUserIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	private final ObjectMapper mapper =
			new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.registerModule(new JavaTimeModule());

	@Test
	@Transactional
	public void givenCorrectUserCreate_whenCreateUser_thenCorrect() throws Exception {
		var response = mockMvc.perform(MockMvcRequestBuilders
						.post(UserHelper.userUrlPath)
						.content(mapper.writeValueAsString(UserHelper.createUserCreate()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();

		UserResponse userResponse =
				mapper.readValue(response.getResponse().getContentAsString(), UserResponse.class);

		assertEquals(UserHelper.login, userResponse.login());
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	@Transactional
	public void givenIncorrectUserCreateExistingName_whenCreateUser_thenException()
			throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.post(UserHelper.userUrlPath)
						.content(mapper.writeValueAsString(UserHelper.createUserCreate()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		var response = mockMvc.perform(MockMvcRequestBuilders
						.post(UserHelper.userUrlPath)
						.content(mapper.writeValueAsString(UserHelper.createUserCreate()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		String errorMessage = response.getResponse().getContentAsString();

		assertTrue(errorMessage.contains(LoginAlreadyExists));
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	@Transactional
	public void givenIncorrectUserCreateBlankLogin_whenCreateUser_thenException()
			throws Exception {
		var response = mockMvc.perform(MockMvcRequestBuilders
						.post(UserHelper.userUrlPath)
						.content(mapper.writeValueAsString(UserHelper.createUserCreateBlankLogin()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		String errorMessage = response.getResponse().getContentAsString();

		assertTrue(errorMessage.contains(LoginIsRequiredMessage));
		assertEquals(0, userRepository.findAll().size());
	}

	@Test
	@Transactional
	public void givenCorrectUserUpdate_whenUpdateUser_thenCorrect() throws Exception {
		User user = userRepository.save(UserHelper.createUser());

		var response = mockMvc.perform(MockMvcRequestBuilders
						.put(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
						.content(mapper.writeValueAsString(UserHelper.createUserUpdate()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		UserResponse userResponse =
				mapper.readValue(response.getResponse().getContentAsString(), UserResponse.class);

		assertEquals(user.getId(), userResponse.id());
		assertEquals(UserHelper.updateLogin, userResponse.login());
		assertEquals(
				UserHelper.updateLogin,
				userRepository.findById(user.getId()).get().getLogin()
		);
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	@Transactional
	public void givenIncorrectUserUpdateBlankLogin_whenUpdateUser_thenException()
			throws Exception {
		User user = userRepository.save(UserHelper.createUser());

		var response = mockMvc.perform(MockMvcRequestBuilders
						.put(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
						.content(mapper.writeValueAsString(UserHelper.createUserUpdateBlankLogin()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		String errorMessage = response.getResponse().getContentAsString();

		assertTrue(errorMessage.contains(LoginIsRequiredMessage));
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	@Transactional
	public void givenIncorrectUserUpdateLoginAlreadyExists_whenUpdateUser_thenException()
			throws Exception {
		User user = userRepository.save(UserHelper.createUser());

		var response = mockMvc.perform(MockMvcRequestBuilders
						.put(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
						.content(mapper.writeValueAsString(UserHelper.createUserUpdateWithExistingLogin()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		String errorMessage = response.getResponse().getContentAsString();

		assertTrue(errorMessage.contains(LoginAlreadyExists));
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	@Transactional
	public void givenIncorrectUserCreateLoginAlreadyExists_whenUpdateUser_thenException()
			throws Exception {
		User user = userRepository.save(UserHelper.createUser());

		var response = mockMvc.perform(MockMvcRequestBuilders
						.put(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
						.content(mapper.writeValueAsString(UserHelper.createUser()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		String errorMessage = response.getResponse().getContentAsString();

		assertTrue(errorMessage.contains(LoginAlreadyExists));
		assertEquals(1, userRepository.findAll().size());
	}

	@Test
	@Transactional
	public void givenIncorrectUserUpdateIdNotExists_whenUpdateUser_thenException()
			throws Exception {
		List<User> userList = UserHelper.prepareUserList();
		userList.forEach(a -> userRepository.save(a));
		Long invalidId = 100L;

		var response = mockMvc.perform(MockMvcRequestBuilders
						.put(createUrlPathWithId(UserHelper.userUrlPath, invalidId))
						.content(mapper.writeValueAsString(UserHelper.createUserUpdate()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		String errorMessage = response.getResponse().getContentAsString();

		assertEquals(
				createEntityNotExistsMessage(User.class.getSimpleName(), invalidId),
				errorMessage
		);
	}

	@Test
	@Transactional
	public void givenCorrectId_whenDeleteUser_thenCorrect() throws Exception {
		List<User> userList = UserHelper.prepareUserList();
		userList.forEach(a -> userRepository.save(a));
		User user = userRepository.findAll().stream().findFirst().get();

		mockMvc.perform(MockMvcRequestBuilders
						.delete(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		assertEquals(UserHelper.testUsersCount - 1, userRepository.findAllByStatus(UserStatusEnum.ENABLED).size());
	}

	@Test
	@Transactional
	public void givenIncorrectId_whenDeleteUser_thenException() throws Exception {
		List<User> userList = UserHelper.prepareUserList();
		userList.forEach(a -> userRepository.save(a));
		Long invalidId = 10000L;

		var response = mockMvc.perform(MockMvcRequestBuilders
						.delete(createUrlPathWithId(UserHelper.userUrlPath, invalidId))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		String errorMessage = response.getResponse().getContentAsString();

		assertEquals(
				createEntityNotExistsMessage(User.class.getSimpleName(), invalidId),
				errorMessage
		);
		assertEquals(UserHelper.testUsersCount, userRepository.findAll().size());
	}
}
