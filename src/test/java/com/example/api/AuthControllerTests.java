package com.example.api;

import com.example.api.utils.AuthUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private AuthUtils authUtils;


	@BeforeEach
	void setUp (){
		this.authUtils  = new AuthUtils(this.mockMvc);
	}

	@Test
	void whenUserAccessHelloWithoutLogin_shouldBeUnauthorized() throws Exception {
		this.mockMvc.perform(get("/qrs"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.error").value("Full authentication is required to access this resource"));
	}

	@Test
	void whenUserAccessHelloWithLogin_shouldSucceed() throws Exception {
		authUtils.registerUserTest();

		String token = authUtils.loginUserTest();

		this.mockMvc.perform(get("/qrs")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/hal+json"));
	}

	@Test
	void whenUserRegisterWithAnExistentUsername_shouldFail() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		authUtils.registerUserTest();

		HashMap<String, String> bodyUserTwo = new HashMap<>();
		bodyUserTwo.put("username", authUtils.getUsernameTest()); //same username
		bodyUserTwo.put("password", "anotherPass1234");
		bodyUserTwo.put("email", "another_email@gmail.com");

		this.mockMvc.perform(post("/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(bodyUserTwo)))
				.andExpect(status().isBadRequest());
	}
}
