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
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.system.kenshinsystem.dto.ReadingDTO;
import com.system.kenshinsystem.security.controller.AuthenticationRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = KenshinSystemApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KenshinSystemReadingsTests {

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
	void itShouldReturnReadings() throws Exception {
		String json = mvc.perform(MockMvcRequestBuilders.get("/api/kenshin/central/floor/reading")
						.header("Authorization", "Bearer "+jwt)
						.param("building_name", "Sample Building C")
						.param("reading_date", "2023-10-01")
						.param("floor_name", "1F"))
						.andExpect(status().isOk())
						.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		ObjectMapper objectMapper = JsonMapper.builder()
										    .addModule(new JavaTimeModule())
										    .build();
		ReadingDTO readingDTO = objectMapper.readValue(json, ReadingDTO.class);
		assertThat(readingDTO.getFloorName()).isEqualTo("1F");
		assertThat(readingDTO.getBuildingName()).isEqualTo("Sample Building C");
		assertThat(readingDTO.getReadingDate()).isEqualTo(LocalDate.of(2023, 10, 1));
	}
	@Test
	void itShouldUpdateReadings() throws Exception {
		
		ReadingDTO readingDTO = ReadingDTO.builder()
										  .buildingName("Sample Building C")
										  .floorName("1F")
										  .readingDate(LocalDate.of(2023, 10, 1))
										  .comment("New Comment")
										  .readings(new Double[]{10.0d,10.0d,10.0d,10.0d})
										  .readingsBeforeChange(new Double[] {0d,0d,0d,0d})
										  .build();
		ObjectMapper objectMapper = JsonMapper.builder()
											  .addModule(new JavaTimeModule())
											  .build();
		String readingDtoJson = objectMapper.writeValueAsString(readingDTO);
		mvc.perform(MockMvcRequestBuilders.put("/api/kenshin/central/floor/update_readings")
						 .header("Authorization", "Bearer "+jwt)
						 .param("building_name", "Sample Building C")
						 .param("reading_date", "2023-10-01")
						 .param("floor_name", "1F")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(readingDtoJson))
						 .andExpect(status().isOk())
						 .andExpect(content().string("Readings updated successfully."));
	}
}
