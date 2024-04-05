package com.example.catalog.integration.user;

import static com.example.catalog.util.ErrorMessagesConstants.createEntityNotExistsMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;
import java.util.List;
import com.example.catalog.user.UserHelper;
import com.example.catalog.user.entity.User;
import com.example.catalog.user.mapper.UserMapper;
import com.example.catalog.user.repository.UserRepository;
import com.example.catalog.user.response.UserResponse;
import com.example.catalog.shared.AbstractIntegrationTest;
import com.example.catalog.shared.RestPageImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;

public class ViewUserIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

	@Test
	@Transactional
	public void givenCorrectId_whenGetUserById_theReturnUserResponseCorrect() throws Exception {
		User expectedUser = UserHelper.createUser();
		expectedUser = userRepository.save(expectedUser);

		var response = mockMvc.perform(get(createUrlPathWithId(UserHelper.userUrlPath, expectedUser.getId()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		UserResponse userResponse = mapper.readValue(response.getResponse().getContentAsString(), UserResponse.class);

		assertEquals(expectedUser.getId(), userResponse.id());
		assertEquals(expectedUser.getLogin(), userResponse.login());

		assertThat(userRepository.getReferenceById(expectedUser.getId())).isEqualTo(expectedUser);
	}

	@Test
	@Transactional
	public void givenIncorrectId_whenGetById_thenException() throws Exception {
		List<User> userList = UserHelper.prepareUserList();
		userList.forEach(a -> userRepository.save(a));
		Long invalidId = 10000L;

		var response = mockMvc.perform(get(createUrlPathWithId(UserHelper.userUrlPath, invalidId))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		String errorMessage = response.getResponse().getContentAsString();
		assertTrue(errorMessage.contains(createEntityNotExistsMessage(User.class.getSimpleName(), invalidId)));

	}

	@Test
	@Transactional
	public void testGetAllUsers_defaultPageRequest() throws Exception {
		List<User> userList = UserHelper.prepareUserList();
		userList.forEach(a -> userRepository.save(a));

		var response = mockMvc.perform(get(UserHelper.userUrlPath)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$['pageable']['paged']").value("true"))
				.andReturn();
		Page<UserResponse> usersResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<UserResponse>>() {});

		assertFalse(usersResponse.isEmpty());
		assertEquals( UserHelper.testUsersCount, usersResponse.getTotalElements());
		assertEquals(2, usersResponse.getTotalPages());
		assertEquals(5, usersResponse.getContent().size());

		for (int i = 0; i < 5; i++) {
			AssertionsForClassTypes.assertThat(usersResponse.getContent().get(i))
					.usingRecursiveComparison()
					.ignoringFields("id")
					.isEqualTo(userMapper.mapEntityToResponse(userList.get(i)));
		}
	}

	@Test
	@Transactional
	public void testGetAllUsers_empty() throws Exception {
		var response = mockMvc.perform(get(UserHelper.userUrlPath)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$['pageable']['paged']").value("true"))
				.andReturn();
		Page<UserResponse> usersResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<UserResponse>>() {});

		assertTrue(usersResponse.isEmpty());
		assertEquals( 0, usersResponse.getTotalElements());
		assertEquals(0, usersResponse.getTotalPages());
		assertEquals(0, usersResponse.getContent().size());
	}

	@Test
	@Transactional
	public void testGetAllUsers_customPageRequest() throws Exception {
		List<User> userList = UserHelper.createListWithUsers(10);
		userList.forEach(a -> userRepository.save(a));
		Page<User> expectedUserList = userRepository.findAll(PageRequest.of(1, 3, Sort.by("id").descending())) ;

		var response = mockMvc.perform(get(createUrlPathGetPageable(UserHelper.userUrlPath, 1, 3, "id", false))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$['pageable']['paged']").value("true"))
				.andReturn();
		Page<UserResponse> usersResponse = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<RestPageImpl<UserResponse>>() {});

		assertFalse(usersResponse.isEmpty());
		assertEquals(expectedUserList.getTotalElements(), usersResponse.getTotalElements());
		assertEquals(expectedUserList.getTotalPages(), usersResponse.getTotalPages());
		assertEquals(expectedUserList.getContent().size(), usersResponse.getContent().size());

		for (int i = 0; i < expectedUserList.getContent().size(); i++) {
			AssertionsForClassTypes.assertThat(usersResponse.getContent().get(i))
					.usingRecursiveComparison()
					.ignoringFields("id")
					.isEqualTo(userMapper.mapEntityToResponse(expectedUserList.getContent().get(i)));
		}
	}
}
