package com.system.kenshinsystem.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import com.system.kenshinsystem.dto.ReadingDTO;
import com.system.kenshinsystem.model.Readings;

public interface ReadingsService {
	
	Readings getReadings(String buildingName,LocalDate readingDate,String floorName);
	LinkedHashMap<String,Readings> getReadings(String buildingName,LocalDate readingDate);
	String updateReadings(String buildingName, LocalDate readingDate, String floorName, ReadingDTO readingDTO);

}
