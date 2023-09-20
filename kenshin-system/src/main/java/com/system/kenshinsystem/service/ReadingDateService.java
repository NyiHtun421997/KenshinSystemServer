package com.system.kenshinsystem.service;

import java.time.LocalDate;

import com.system.kenshinsystem.model.ReadingDate;

public interface ReadingDateService {
	
	LocalDate getLatestDateByBuildingName(String buildingName);
	ReadingDate findByReadingDate(LocalDate readingDate);

}
