package com.system.kenshinsystem.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReadingDTO {
	
	private Double[] readings = new Double[4];//電灯、動力、水道、ガス
	private Double[] readingsBeforeChange;
	private String buildingName,floorName;
	private LocalDate readingDate;
	private String comment;
	
}
