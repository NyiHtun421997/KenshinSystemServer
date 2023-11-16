package com.system.kenshinsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.system.kenshinsystem.model.ReadingDate;

public interface ReadingDateService {
	
	LocalDate getLatestDateByBuildingName(String buildingName);
	ReadingDate findByReadingDate(LocalDate readingDate);
	List<ReadingDate> getReadingDateByBuildingName(String buildingName);
	ReadingDate createReadingDate(LocalDate readingDate,String buildingName);

}
