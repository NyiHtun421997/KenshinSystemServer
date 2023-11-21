package com.system.kenshinsystem.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import com.system.kenshinsystem.dto.ReadingDTO;
import com.system.kenshinsystem.model.Readings;

public interface ReadingsService {
	
	Readings getReadings(String buildingName,LocalDate readingDate,String floorName);
	String updateReadings(String buildingName, LocalDate readingDate, String floorName, ReadingDTO readingDTO);
	String storeReadings(LinkedHashMap<String,ReadingDTO> floorMap,String buildingName,LocalDate readingDate);
	Map<String, ReadingDTO> getTenantReadingsFromDb(String buildingName, LocalDate readingDate);
	Map<String, ReadingDTO> getFloorReadingsFromDb (String buildingName, LocalDate readingDate);
	Map<String, ReadingDTO> getTenantReadingsFromTempMap(String buildingName);
}
