package com.system.kenshinsystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.kenshinsystem.dto.ReadingDTO;
import com.system.kenshinsystem.mapper.ReadingMapper;
import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.model.Floor;
import com.system.kenshinsystem.model.Readings;
import com.system.kenshinsystem.model.Tenant;
import com.system.kenshinsystem.service.BuildingService;
import com.system.kenshinsystem.service.FloorService;
import com.system.kenshinsystem.service.ReadingDateService;
import com.system.kenshinsystem.service.ReadingsService;
import com.system.kenshinsystem.service.TempMapService;
import com.system.kenshinsystem.service.TenantService;

@RestController
@RequestMapping(path="api/kenshin/central")
public class CentralController {
	
	private final ReadingDateService readingDateService;
	private final BuildingService buildingService;
	private final FloorService floorService;
	private final TenantService tenantService;
	private final ReadingsService readingsService;
	private final TempMapService tempMapService;
	
	@Autowired
	public CentralController(ReadingDateService readingDateService,BuildingService buildingService,
			FloorService floorService,TenantService tenantService,ReadingsService readingsService,
			TempMapService tempMapService) {
		
		this.readingDateService = readingDateService;
		this.buildingService = buildingService;
		this.floorService = floorService;
		this.tenantService = tenantService;
		this.readingsService = readingsService;
		this.tempMapService = tempMapService;
	}
	
	@GetMapping("/latest_date")
	public String getLatestDayForBuilding(@RequestParam(name = "building_name",required = false)String buildingName) {
		
		LocalDate latestDate = this.readingDateService.getLatestDateByBuildingName(buildingName);
		return latestDate.getYear()+"-"+Integer.toString(latestDate.getMonthValue()+1);
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
	public List<String> getTenantListByBldName(@RequestParam(name = "building_name",required = false)String buildingName){
		
		List<String> tenants = this.tenantService.getTenantListByBuildingName(buildingName);
		
		if(tenants == null) {
			return null;
		}
		
		return tenants;
		
	}
	
	@GetMapping("/readings")
	public ReadingDTO getFloorReadings(@RequestParam(name = "building_name",required = false)String buildingName,
		@RequestParam(name = "reading_date",required = false)LocalDate readingDate,
		@RequestParam(name = "floor_name",required = false)String floorName){
		
		Readings readings = this.readingsService.getReadings(buildingName, readingDate, floorName);
		
		return ReadingMapper.mapToReadingDTO(readings);
		
	}
	@PostMapping("/temporary/save_readings")
	public void storeReadingsInTempMap(@RequestBody ReadingDTO readingDTO) {
		
		tempMapService.addReading(readingDTO);
	}
	//end point for converting readings for each floor from temporary storage into readings for each tenant
	//this will be passed to CS01
	@GetMapping("/temporary/get_readings")
	public LinkedHashMap<String,ReadingDTO> getReadingsFromTempMap(@RequestParam(name = "building_name", required = false)String buildingName) {
		
		LinkedHashMap<String,ReadingDTO> tenant_reading_map = new LinkedHashMap<>();
		
		//will get all the readings for all floors of a building
		LinkedHashMap<String,ReadingDTO> floor_reading_map = (LinkedHashMap<String, ReadingDTO>) this.tempMapService.getReadingsForBuilding(buildingName);
		List<String> floor_tenants = this.tenantService.getTenantListByBuildingName(buildingName);
		
		//get a list of floor_tanant and iterate through each
		for(String floor_tenant : floor_tenants) {
			
			//substring floor_name・tenant_name format into tenant_name
			String tenantName = floor_tenant.substring(floor_tenant.indexOf("・")+1);
			
			Tenant tenant = this.tenantService.findByTenantName(tenantName);
			//find reading from TempMap for the floor that tenant occupies
			
			//will multiply each reading with this area ratio to get reading for each tenant
			Double areaRatio = this.tenantService.getAreaRatio(tenantName);
			System.out.println("***************************************"+areaRatio);
			ReadingDTO readingDTO = floor_reading_map.get(tenant.getFloor().getName());
			//multiplying with area ratio for each tenant reading
			Double[] oldReadings =  readingDTO.getReadings();
			Double[] newReadings = { oldReadings[0]*areaRatio, oldReadings[1]*areaRatio, oldReadings[2]*areaRatio, oldReadings[3]*areaRatio };
			System.out.println(oldReadings[0]+","+oldReadings[1]+","+oldReadings[2]+","+oldReadings[3]+"**********************");
			System.out.println(newReadings[0]+","+newReadings[1]+","+newReadings[2]+","+newReadings[3]+"**********************");
			readingDTO.setReadings(newReadings);
			
			//multiplying with area ratio for each tenant reading before change
			Double[] oldReadingsBeforeChange =  readingDTO.getReadingsBeforeChange();
			Double[] newReadingsBeforeChange = { oldReadingsBeforeChange[0]*areaRatio, oldReadingsBeforeChange[1]*areaRatio, oldReadingsBeforeChange[2]*areaRatio, oldReadingsBeforeChange[3]*areaRatio };
			readingDTO.setReadingsBeforeChange(newReadingsBeforeChange);
			
			tenant_reading_map.put(floor_tenant, readingDTO);
		}
		return tenant_reading_map;
		
	}
	//For Testing TempMap
	@GetMapping("/get_temp_readings")
	public List<Object> getAllReadings(){
		List<Object> newList = new ArrayList<>();
		Map<String, Map<String, ReadingDTO>> bld_floorMap = tempMapService.getAllReadings();
		for(String x : bld_floorMap.keySet()) {
			for(String y : bld_floorMap.get(x).keySet()) {
				newList.add(bld_floorMap.get(x).get(y));
			}
		}
		return newList;
	}
	
	//end point for getting readings for each floor from the past and converting into readings for each tenant
	//this will be passed into CS01
	@GetMapping("/past_readings")
	public LinkedHashMap<String,ReadingDTO> getAllReadingsFromPast(@RequestParam(name = "building_name",required = false)String buildingName,
																@RequestParam(name = "reading_date",required = false)LocalDate readingDate){
		LinkedHashMap<String,ReadingDTO> tenant_reading_map = new LinkedHashMap<>();
		
		List<String> floor_tenants = this.tenantService.getTenantListByBuildingName(buildingName);
		
		//get a list of floor_tanant and iterate through each
		for(String floor_tenant : floor_tenants) {
			
			//substring floor_name・tenant_name format into tenant_name
			String tenantName = floor_tenant.substring(floor_tenant.indexOf("・")+1);
			
			Tenant tenant = this.tenantService.findByTenantName(tenantName);
			//find reading from DB for the floor that tenant occupies
			Readings readings = this.readingsService.getReadings(buildingName, readingDate, tenant.getFloor().getName());
			
			//will multiply each reading with this area ratio to get reading for each tenant
			Double areaRatio = this.tenantService.getAreaRatio(tenantName);
			
			ReadingDTO readingDTO = ReadingMapper.mapToTenantReadingDTO(readings, areaRatio);
			
			tenant_reading_map.put(floor_tenant, readingDTO);
		}
		
		return tenant_reading_map;
	}
	
	@GetMapping("/temporary/check")
	public Boolean checkForBuilding(@RequestParam(name = "building_name",required = false)String buildingName) {
		
		return this.tempMapService.doesBuildingDataExist(buildingName);
	}
	
	

}
