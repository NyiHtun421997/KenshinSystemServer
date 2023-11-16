package com.system.kenshinsystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.system.kenshinsystem.dto.ReadingDTO;
import com.system.kenshinsystem.mapper.ReadingMapper;
import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.model.Floor;
import com.system.kenshinsystem.model.ReadingDate;
import com.system.kenshinsystem.model.Readings;
import com.system.kenshinsystem.model.Tenant;
import com.system.kenshinsystem.service.BuildingService;
import com.system.kenshinsystem.service.FloorService;
import com.system.kenshinsystem.service.PhotoService;
import com.system.kenshinsystem.service.ReadingDateService;
import com.system.kenshinsystem.service.ReadingsService;
import com.system.kenshinsystem.service.TempMapService;
import com.system.kenshinsystem.service.TenantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="api/kenshin/central")
@RequiredArgsConstructor
public class CentralController {
	
	private final ReadingDateService readingDateService;
	private final BuildingService buildingService;
	private final FloorService floorService;
	private final TenantService tenantService;
	private final ReadingsService readingsService;
	private final TempMapService tempMapService;
	
	@GetMapping("/latest_date")
	public ResponseEntity<?> getLatestDayForBuilding(@RequestParam(name = "building_name",required = false)String buildingName) {
		
		LocalDate latestDate = this.readingDateService.getLatestDateByBuildingName(buildingName);
		latestDate = latestDate.plusMonths(1);
		return ResponseEntity.ok(latestDate);
	}
	@GetMapping("/reading_dates")
	public ResponseEntity<?> getReadingDatesForBuilding(@RequestParam(name = "building_name",required = false)String buildingName){
		
		List<ReadingDate> readingDateObjs = this.readingDateService.getReadingDateByBuildingName(buildingName);
		
		List<LocalDate> readingDates =  readingDateObjs.stream()
									.map(ReadingDate :: getDate)
									.collect(Collectors.toList());
		return ResponseEntity.ok(readingDates);
	}
	
	@GetMapping("/building_names")
	public ResponseEntity<?> getBuildingNames(){
		
		List<Building> buildingObjs =  this.buildingService.getBuildingNames();
		List<String> buildingNames = buildingObjs.stream()
												.map(Building::getName)
												.collect(Collectors.toList());		
		return ResponseEntity.ok(buildingNames);
	}
	
	@GetMapping("/floors")
	public ResponseEntity<?> getFloorListByBldName(@RequestParam(name = "building_name",required = false)String buildingName) {
		
		List<Floor> floors = this.floorService.getFloorListByBuildingName(buildingName);
		List<String> floorNames = floors.stream()
										.map(Floor::getName)
										.collect(Collectors.toList());
		return ResponseEntity.ok(floorNames);		
	}
	
	@GetMapping("/tenants")
	public ResponseEntity<?> getTenantListByBldName(@RequestParam(name = "building_name",required = false)String buildingName){
		
		List<String> tenants = this.tenantService.getTenantListByBuildingName(buildingName);	
		return ResponseEntity.ok(tenants);	
	}
	
	@GetMapping("floor/reading")
	public ReadingDTO getFloorReading(@RequestParam(name = "building_name",required = false)String buildingName,
		@RequestParam(name = "reading_date",required = false)LocalDate readingDate,
		@RequestParam(name = "floor_name",required = false)String floorName){
		
		Readings readings = this.readingsService.getReadings(buildingName, readingDate, floorName);
		
		return ReadingMapper.mapToReadingDTO(readings);	
	}
	@PutMapping("floor/update_readings")
	public ResponseEntity<String> updateFloorReadings(@RequestParam(name = "building_name",required = false)String buildingName,
			@RequestParam(name = "reading_date",required = false)LocalDate readingDate,
			@RequestParam(name = "floor_name",required = false)String floorName,@RequestBody ReadingDTO readingDTO){
		
		String message = this.readingsService.updateReadings(buildingName, readingDate, floorName, readingDTO);
		if(message == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Error: Readings do not exist.");
		}
		return ResponseEntity.ok(message);
	}
	//this will be passed into CH01
	@GetMapping("floor/past_readings")
	public ResponseEntity<?> getFloorReadings(@RequestParam(name = "building_name",required = false)String buildingName,
			@RequestParam(name = "reading_date",required = false)LocalDate readingDate){
		
		LinkedHashMap<String,Readings> floorReadingsMap = this.readingsService.getReadings(buildingName, readingDate);
		if(floorReadingsMap == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Error: Readings do not exist.");
		}
		LinkedHashMap<String,ReadingDTO> floorReadingDTOMap = new LinkedHashMap<>();
		for(Map.Entry<String,Readings> x : floorReadingsMap.entrySet()) {
			floorReadingDTOMap.put(x.getKey(),ReadingMapper.mapToReadingDTO(x.getValue()));
		}
		return ResponseEntity.ok(floorReadingDTOMap);
	}
	//end point for getting readings for each floor from the past and converting into readings for each tenant
	//this will be passed into CS01
	@GetMapping("tenant/past_readings")
	public ResponseEntity<?> getTenantReadings(@RequestParam(name = "building_name",required = false)String buildingName,
											@RequestParam(name = "reading_date",required = false)LocalDate readingDate){
		LinkedHashMap<String,ReadingDTO> tenant_reading_map = new LinkedHashMap<>();		
		List<String> floor_tenants = this.tenantService.getTenantListByBuildingName(buildingName);
			
		//get a list of floor_tenant and iterate through each
		for(String floor_tenant : floor_tenants) {			
			//substring floor_name・tenant_name format into tenant_name
			String tenantName = floor_tenant.substring(floor_tenant.indexOf("・")+1);
			//since tenants can have same names,we need to add extra parameter to distinguish
			String floorName = floor_tenant.substring(0,floor_tenant.indexOf("・"));
			Tenant tenant = this.tenantService.findByFloorNameAndBuildingId(tenantName,floorName,buildingName);
			//find reading from DB for the floor that tenant occupies
			Readings readings = this.readingsService.getReadings(buildingName, readingDate, tenant.getFloor().getName());			
			//will multiply each reading with this area ratio to get reading for each tenant
			Double areaRatio = this.tenantService.getAreaRatio(tenantName,floorName,buildingName);			
			ReadingDTO readingDTO = ReadingMapper.mapToTenantReadingDTO(readings, areaRatio);			
			tenant_reading_map.put(floor_tenant, readingDTO);
		}		
		return ResponseEntity.ok(tenant_reading_map);
	}
	@PostMapping("/temporary/save_readings")
	public void storeReadingsInTempMap(@RequestBody ReadingDTO readingDTO) {
		
		this.tempMapService.addReading(readingDTO);
	}
	@PostMapping("/temporary/save_comments")
	public void storeComments(@RequestParam(name = "building_name",required = false)String buildingName,
			@RequestBody LinkedHashMap<String,String> commentData){
		
		if(this.tempMapService.doesBuildingDataExist(buildingName)) {
			
			this.tempMapService.storeComments(buildingName, commentData);
		}
	}
	//end point for converting readings for each floor from temporary storage into readings for each tenant
	//this will be passed to CS01
	@GetMapping("/temporary/tenant/get_readings")
	public ResponseEntity<?> getTenantReadingsFromTempMap(@RequestParam(name = "building_name", required = false)String buildingName) {
			
		//will get all the readings for all floors of a building
		LinkedHashMap<String,ReadingDTO> floor_reading_map = (LinkedHashMap<String, ReadingDTO>) this.tempMapService.getReadingsForBuilding(buildingName);
		if(floor_reading_map == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("Error: Readings do no exist:");
		}
		LinkedHashMap<String,ReadingDTO> tenant_reading_map = new LinkedHashMap<>();	
		List<String> floor_tenants = this.tenantService.getTenantListByBuildingName(buildingName);		
		//get a list of floor_tanant and iterate through each
		for(String floor_tenant : floor_tenants) {
			//substring floor_name・tenant_name format into tenant_name
			String tenantName = floor_tenant.substring(floor_tenant.indexOf("・")+1);
			//since tenants can have same names,we need to add extra parameter to distinguish
			String floorName = floor_tenant.substring(0,floor_tenant.indexOf("・"));
			Tenant tenant = this.tenantService.findByFloorNameAndBuildingId(tenantName,floorName,buildingName);
			//find reading from TempMap for the floor that tenant occupies			
			//will multiply each reading with this area ratio to get reading for each tenant
			Double areaRatio = this.tenantService.getAreaRatio(tenantName,floorName,buildingName);
			ReadingDTO readingDTO = floor_reading_map.get(tenant.getFloor().getName());
			//call the mapper method to convert readings for each floor from temporary storage to readings for each tenant
			ReadingDTO newReadingDTO = ReadingMapper.floorToTenantReadingDTO(readingDTO, areaRatio);			
			tenant_reading_map.put(floor_tenant, newReadingDTO);
		}
		return ResponseEntity.ok(tenant_reading_map);
		
	}
	//this will be passed into CH01
	@GetMapping("/temporary/floor/get_readings")
	public ResponseEntity<?> getFloorReadingsFromTempMap(@RequestParam(name = "building_name",required = false)String buildingName) {
		LinkedHashMap<String,ReadingDTO> floor_reading_map = (LinkedHashMap<String, ReadingDTO>) this.tempMapService.getReadingsForBuilding(buildingName);
		if(floor_reading_map == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("Error: Readings do no exist:");
		}
		return ResponseEntity.ok(floor_reading_map);
	}
	
	@GetMapping("/temporary/check")
	public Boolean checkForBuilding(@RequestParam(name = "building_name",required = false)String buildingName) {
		
		return this.tempMapService.doesBuildingDataExist(buildingName);
	}
	
	//For Testing TempMap
	@GetMapping("/get_temp_readings")
	public Map<String,Object> getAllReadings(){
		Map<String,Object> newList = new LinkedHashMap<>();
		Map<String, Map<String, ReadingDTO>> bld_floorMap = tempMapService.getAllReadings();
		for(Object o : bld_floorMap.keySet()) {
			for(String y : bld_floorMap.get(o).keySet()) {
				//newList.add(bld_floorMap.get(o).get(y));
				newList.put(o.toString(),bld_floorMap.get(o).get(y));
			}
		}
		return newList;
	}
	
	
	@PostMapping("/temporary/final_approve")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public void finalApprove(@RequestParam(name = "building_name",required = false)String buildingName,
			@RequestParam(name = "reading_date",required = false)LocalDate readingDate){	
			
			if(this.tempMapService.doesBuildingDataExist(buildingName)) {
			//get floorMap and save them to DB
			LinkedHashMap<String, ReadingDTO> floorMap = this.tempMapService.finalApproveReadings(buildingName);
			if(floorMap!=null)
			this.readingsService.storeReadings(floorMap, buildingName, readingDate);
		}
	}
	@PostMapping("/temporary/approve")
	public void approve(@RequestParam(name = "building_name",required = false)String buildingName,
			@RequestParam(name = "reading_date",required = false)LocalDate readingDate){
		this.tempMapService.approveReadings(buildingName);
	}
}
