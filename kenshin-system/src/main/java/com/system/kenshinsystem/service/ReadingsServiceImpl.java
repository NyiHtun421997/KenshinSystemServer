package com.system.kenshinsystem.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.kenshinsystem.dto.ReadingDTO;
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
	
	@Override
	public LinkedHashMap<String, Readings> getReadings(String buildingName, LocalDate date){
		
		LinkedHashMap<String,Readings> floorReadingsMap = new LinkedHashMap<>();
		Building building = this.buildingService.findByBuildingName(buildingName);
		ReadingDate readingDate = this.readingDateService.findByReadingDate(date);
		List<Floor> floors = building.getFloors();
		
		for(Floor floor : floors) {
			floorReadingsMap.put(floor.getName(),this.readingsRepository.findByFloorIdAndReadingDateId(floor.getId(), readingDate.getId()));
		}
		return floorReadingsMap;
	}

	@Override
	public String updateReadings(String buildingName, LocalDate readingDate, String floorName, ReadingDTO readingDTO) {
		
		Readings readings = getReadings(buildingName,readingDate,floorName);
		if(readings == null) {
			return null;
		}
		
		Double[] readingsDouble = readingDTO.getReadings();
		Double[] reaingsBeforeChangeDouble = readingDTO.getReadingsBeforeChange();
		
		readings.setLightingReading(readingsDouble[0]);
		readings.setPowerReading(readingsDouble[1]);
		readings.setWaterReading(readingsDouble[2]);
		readings.setGasReading(readingsDouble[3]);
		
		readings.setLightingReadingBeforeChange(reaingsBeforeChangeDouble[0]);
		readings.setPowerReadingBeforeChange(reaingsBeforeChangeDouble[1]);
		readings.setWaterReadingBeforeChange(reaingsBeforeChangeDouble[2]);
		readings.setGasReadingBeforeChange(reaingsBeforeChangeDouble[3]);
		
		readings.setComment(readingDTO.getComment());
		
		this.readingsRepository.save(readings);
		return "Readings updated successfully.";
	}

}
