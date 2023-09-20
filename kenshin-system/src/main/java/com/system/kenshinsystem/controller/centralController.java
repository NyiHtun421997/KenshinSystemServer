package com.system.kenshinsystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.model.Floor;
import com.system.kenshinsystem.model.Readings;
import com.system.kenshinsystem.model.Tenant;
import com.system.kenshinsystem.service.BuildingService;
import com.system.kenshinsystem.service.FloorService;
import com.system.kenshinsystem.service.ReadingDateService;
import com.system.kenshinsystem.service.ReadingsService;
import com.system.kenshinsystem.service.TenantService;

@RestController
@RequestMapping(path="api/kenshin/central")
public class centralController {
	
	private final ReadingDateService readingDateService;
	private final BuildingService buildingService;
	private final FloorService floorService;
	private final TenantService tenantService;
	private final ReadingsService readingsService;
	
	@Autowired
	public centralController(ReadingDateService readingDateService,BuildingService buildingService,
			FloorService floorService,TenantService tenantService,ReadingsService readingsService) {
		
		this.readingDateService = readingDateService;
		this.buildingService = buildingService;
		this.floorService = floorService;
		this.tenantService = tenantService;
		this.readingsService = readingsService;
	}
	
	@GetMapping("/latest_date")
	public LocalDate getLatestDayForBuilding(@RequestParam(name = "building_name",required = false)String buildingName) {
		
		return this.readingDateService.getLatestDateByBuildingName(buildingName);
	}
	
	@GetMapping("/building_names")
	public List<String> getBuildingNames(){
		
		List<Building> buildings =  this.buildingService.getBuildingNames();
		List<String> buildingNames = new ArrayList<>();
		
		if(buildings == null) {
			return null;
		}
		for(Building x : buildings) {
			
			buildingNames.add(x.getName());
		
		}
		return buildingNames;
	}
	
	@GetMapping("/floors")
	public List<String> getFloorListByBldName(@RequestParam(name = "building_name",required = false)String buildingName) {
		
		List<Floor> floors = this.floorService.getFloorListByBuildingName(buildingName);
		List<String> floorNames = new ArrayList<>();
		if(floors == null) {
			return null;
		}
		for(Floor x : floors) {
			
			floorNames.add(x.getName());
		
		}
		return floorNames;
		
	}
	
	@GetMapping("/tenants")
	public List<String> getTenantListByBldNameAndFloorName(@RequestParam(name = "building_name",required = false)String buildingName
			,@RequestParam(name = "floor_name",required = false)String floorName){
		
		List<Tenant> tenants = this.tenantService.getTenantListByBuildingNameAndFloorName(buildingName, floorName);
		List<String> tenantNames = new ArrayList<>();
		if(tenants == null) {
			return null;
			
		}
		for(Tenant x : tenants) {
			tenantNames.add(x.getName());
		}
		return tenantNames;
		
	}
	
	@GetMapping("/readings")
	public Readings getFloorReadings(@RequestParam(name = "building_name",required = false)String buildingName,
		@RequestParam(name = "reading_date",required = false)LocalDate readingDate,
		@RequestParam(name = "floor_name",required = false)String floorName){
		
		Readings readings = this.readingsService.getReadings(buildingName, readingDate, floorName);
		
		/*List<Double> readingsToReturn = new ArrayList<>();
		
		//the order must follow 電灯、動力、水道、ガス
		readingsToReturn.add(readings.getLightingReading());
		readingsToReturn.add(readings.getPowerReading());
		readingsToReturn.add(readings.getWaterReading());
		readingsToReturn.add(readings.getGasReading());
		
		return readingsToReturn;*/
		return readings;
		
	}

}
