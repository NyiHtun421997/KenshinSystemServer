package com.system.kenshinsystem.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class readingDTO {
	
	private String buildingName,floorName,tenantName;
	private LocalDate readingDate;
	private Double lightingReading,powerReading,waterReading,gasReading;
	private String lightingURL,powerURL,waterURL,gasURL;

}
