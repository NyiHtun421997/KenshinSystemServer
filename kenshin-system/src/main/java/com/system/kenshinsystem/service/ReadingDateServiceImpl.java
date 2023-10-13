package com.system.kenshinsystem.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.model.ReadingDate;
import com.system.kenshinsystem.repository.ReadingDateRepository;

@Service
public class ReadingDateServiceImpl implements ReadingDateService{
	
	private final ReadingDateRepository readingDateRepository;
	private final BuildingService buildingService;
	
	@Autowired
	public ReadingDateServiceImpl(ReadingDateRepository readingDateRepository,
			BuildingService buildingService) {
		
		this.readingDateRepository = readingDateRepository;
		this.buildingService = buildingService;
	}

	@Override
	public LocalDate getLatestDateByBuildingName(String buildingName) {
		
		Building building = buildingService.findByBuildingName(buildingName);
		List<ReadingDate> readingDates = this.readingDateRepository.findByBuildingId(building.getId());
		
		// Define a comparator to compare ReadingDate entities based on the date attribute
		Comparator<ReadingDate> dateComparator = Comparator.comparing(ReadingDate::getDate);

		// Sort the list in descending order (latest date first)
		Collections.sort(readingDates, dateComparator.reversed());
		
		return readingDates.get(0).getDate();
	}
	@Override
	public ReadingDate findByReadingDate(LocalDate readingDate) {
		
		Optional<ReadingDate> readingDateOptional = this.readingDateRepository.findByDate(readingDate);
		
		if(readingDateOptional.isPresent()) {
			
			return readingDateOptional.get();
		}
		else {
			return null;
		}
	}

	@Override
	public List<ReadingDate> getReadingDateByBuildingName(String buildingName) {
		
		Building building = buildingService.findByBuildingName(buildingName);
		List<ReadingDate> readingDates = this.readingDateRepository.findByBuildingId(building.getId());
		
		return readingDates;
	}

}
