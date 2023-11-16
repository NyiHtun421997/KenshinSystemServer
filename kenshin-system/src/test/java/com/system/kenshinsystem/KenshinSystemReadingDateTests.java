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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.kenshinsystem.security.controller.AuthenticationRequest;

import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = KenshinSystemApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class KenshinSystemReadingDateTests {

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
	void itShouldReturnLatestReadingDate() throws Exception {
						
		String dateString = mvc.perform(MockMvcRequestBuilders.get("/api/kenshin/central/latest_date")
								.header("Authorization", "Bearer "+jwt)
								.param("building_name", "Sample Building C"))
								.andExpect(status().isOk())
								.andReturn().getResponse().getContentAsString();
		assertThat(dateString).isEqualTo("\"2023-11-01\"");

	}
	
	@Test
	void itShouldReturnReadingDates() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/kenshin/central/reading_dates")
			.header("Authorization", "Bearer "+jwt)
			.param("building_name", "Sample Building C"))
			.andExpect(status().isOk());
	}

}
