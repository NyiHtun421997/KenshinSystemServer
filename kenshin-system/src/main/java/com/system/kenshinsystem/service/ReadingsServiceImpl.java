package com.system.kenshinsystem.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.model.Floor;
import com.system.kenshinsystem.model.ReadingDate;
import com.system.kenshinsystem.model.Readings;
import com.system.kenshinsystem.repository.ReadingsRepository;

@Service
public class ReadingsServiceImpl implements ReadingsService{
	
	private final BuildingService buildingService;
	private final ReadingDateService readingDateService;
	private final FloorService floorService;
	private final ReadingsRepository readingsRepository;
	
	@Autowired
	public ReadingsServiceImpl(BuildingService buildingService,ReadingDateService readingDateService,FloorService floorService
			,ReadingsRepository readingsRepository) {
		
		this.buildingService = buildingService;
		this.readingDateService = readingDateService;
		this.floorService = floorService;
		this.readingsRepository = readingsRepository;
	}

	@Override
	public Readings getReadings(String buildingName, LocalDate date, String floorName) {
		
		Building building = this.buildingService.findByBuildingName(buildingName);
		ReadingDate readingDate = this.readingDateService.findByReadingDate(date);
		Floor floor = this.floorService.findFloorByNameAndBuildingId(floorName, building.getId());
		
		return this.readingsRepository.findByFloorIdAndReadingDateId(floor.getId(), readingDate.getId());
		
	}

}
