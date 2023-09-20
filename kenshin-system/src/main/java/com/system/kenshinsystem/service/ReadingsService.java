package com.system.kenshinsystem.service;

import java.time.LocalDate;
import com.system.kenshinsystem.model.Readings;

public interface ReadingsService {
	
	Readings getReadings(String buildingName,LocalDate readingDate,String floorName);

}
