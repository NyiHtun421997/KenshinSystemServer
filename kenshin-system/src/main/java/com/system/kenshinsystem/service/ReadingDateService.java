package com.system.kenshinsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.system.kenshinsystem.model.ReadingDate;

public interface ReadingDateService {
	
	LocalDate getLatestDateByBuildingName(String buildingName);
	ReadingDate findByReadingDate(LocalDate readingDate);
	List<ReadingDate> getReadingDateByBuildingName(String buildingName);

}
