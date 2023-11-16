package com.system.kenshinsystem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.kenshinsystem.security.controller.AuthenticationRequest;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = KenshinSystemApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KenshinSystemBuildingNameTests {

	@Autowired
	private MockMvc mvc;
	
	private String jwt;
	
	@BeforeAll
	void setup() throws Exception {
		AuthenticationRequest authReq = AuthenticationRequest.builder()
														.username("default_user")
														.password("admin")
														.build();
		ObjectMapper objectMapper = new ObjectMapper();
		String authRequestJson = objectMapper.writeValueAsString(authReq);
		jwt = mvc.perform(MockMvcRequestBuilders.post("/api/kenshin/secure/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(authRequestJson))
				.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	void itShouldReturnBuildingNames() throws Exception {
		String json = mvc.perform(MockMvcRequestBuilders.get("/api/kenshin/central/building_names")
						.header("Authorization", "Bearer "+jwt))
						.andExpect(status().isOk())
						.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> buildingNames = objectMapper.readValue(json, new TypeReference<List<String>>() {});
		buildingNames.stream()
					.forEach(n -> assertThat(n).containsAnyOf("Sample Building A","Sample Building B","Sample Building C","Sample Building D"));
	}
}
