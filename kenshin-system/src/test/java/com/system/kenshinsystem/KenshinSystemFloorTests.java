package com.system.kenshinsystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = KenshinSystemApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KenshinSystemFloorTests {

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
	void itShouldReturnFloorNames() throws Exception {
		String json = mvc.perform(MockMvcRequestBuilders.get("/api/kenshin/central/floors")
						.header("Authorization", "Bearer "+jwt)
						.param("building_name", "Sample Building C"))
						.andExpect(status().isOk())
						.andReturn().getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> floorNames = objectMapper.readValue(json, new TypeReference<List<String>>() {});
		floorNames.stream()
					.forEach(n -> assertThat(n).containsAnyOf("1F","2F","3F"));
	}
}
