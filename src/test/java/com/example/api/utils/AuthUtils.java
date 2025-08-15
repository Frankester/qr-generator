package com.example.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthUtils {

    @Setter
    @Getter
    private MockMvc mockMvc;

    @Getter
    private final String usernameTest = "frankester";

    @Getter
    private final String passwordTest = "fran1234";

    @Getter
    private final String emailTest = "francotomascallero@gmail.com";

    public AuthUtils(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public void registerUserTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, String> body = new HashMap<>();
        body.put("username", usernameTest);
        body.put("password", passwordTest);
        body.put("email", emailTest);

        this.getMockMvc().perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)));

    }

    public String loginUserTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, String> body = new HashMap<>();
        body.put("username", usernameTest);
        body.put("password", passwordTest);

        MvcResult result = this.getMockMvc().perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andReturn();
        JsonNode tokenNode = objectMapper.readTree(result.getResponse().getContentAsString());

        return tokenNode.get("token").asText();
    }
}
