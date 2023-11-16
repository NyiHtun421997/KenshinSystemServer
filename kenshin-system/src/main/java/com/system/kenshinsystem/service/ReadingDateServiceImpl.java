package com.system.kenshinsystem.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		List<ReadingDate> readingDates = this.readingDateRepository.findByBuildingId(building.getId())
												.orElseThrow(() -> new NullPointerException("Reading Date not found."));
												
		Comparator<ReadingDate> dateComparator = Comparator.comparing(ReadingDate::getDate);
		
		return readingDates.stream()
							.max(dateComparator)
							.map(ReadingDate::getDate)
							.get();
					
	}
	@Override
	public ReadingDate findByReadingDate(LocalDate readingDate) {
		
		return this.readingDateRepository.findByDate(readingDate)
														.orElseThrow(() -> new NullPointerException("Reading Date not found."));		
	}

	@Override
	public List<ReadingDate> getReadingDateByBuildingName(String buildingName) {
		
		Building building = buildingService.findByBuildingName(buildingName);
		List<ReadingDate> readingDates = this.readingDateRepository.findByBuildingId(building.getId())
												.orElseThrow(() -> new NullPointerException("Reading Date not found."));
		
		return readingDates;
	}

	@Override
	public ReadingDate createReadingDate(LocalDate readingDate,String buildingName) {
		
		ReadingDate newReadingDate = new ReadingDate();
		Building building = this.buildingService.findByBuildingName(buildingName);
		newReadingDate.setDate(readingDate);
		newReadingDate.setBuilding(List.of(building));
		this.readingDateRepository.save(newReadingDate);
		return newReadingDate;
	}

}
