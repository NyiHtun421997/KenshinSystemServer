package com.system.kenshinsystem.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.system.kenshinsystem.dto.ReadingDTO;
import com.system.kenshinsystem.mapper.ReadingMapper;
import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.model.Floor;
import com.system.kenshinsystem.model.ReadingDate;
import com.system.kenshinsystem.model.Readings;
import com.system.kenshinsystem.model.Tenant;
import com.system.kenshinsystem.repository.ReadingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadingsServiceImpl implements ReadingsService{
	
	private final BuildingService buildingService;
	private final ReadingDateService readingDateService;
	private final FloorService floorService;
	private final ReadingsRepository readingsRepository;
	private final TenantService tenantService;
	private final TempMapService tempMapService;

	@Override
	public Readings getReadings(String buildingName, LocalDate date, String floorName) {
		return this.findFloorReading(floorName, buildingName, date);
	}
	private Readings findFloorReading(String floorName, String buildingName, LocalDate date) {
		
		ReadingDate readingDate = this.readingDateService.findByReadingDate(date);
		Building building = this.buildingService.findByBuildingName(buildingName);
		Floor floor = this.floorService.findFloorByNameAndBuildingId(floorName, building.getId());
		return this.readingsRepository.findByFloorIdAndReadingDateId(floor.getId(), readingDate.getId())
				   .orElseThrow(() -> new NullPointerException("Readings do not exist."));
	}
	@Override
	public Map<String, ReadingDTO> getTenantReadingsFromDb(String buildingName, LocalDate date) {

		List<String> floor_tenants = this.tenantService.getTenantListByBuildingName(buildingName);
		//get a list of floor_tenant and iterate through each
		return floor_tenants.stream()
							.collect(Collectors.toMap(Function.identity(),
							floor_tenant -> this.findReadingDTOFromFloor_Tenant(floor_tenant, buildingName, date,
																				this::findReadingAndConvertToTenantReadingDTO),
							(existing, replacement) -> existing,
					        LinkedHashMap::new));
	}
	private ReadingDTO findReadingDTOFromFloor_Tenant(String floor_tenant,String buildingName,LocalDate date,
													  FourParameterFunction<String,String,LocalDate,Double,ReadingDTO> resolver) {
		//substring floor_name・tenant_name format into tenant_name
		String tenantName = floor_tenant.substring(floor_tenant.indexOf("・")+1);
		//since tenants can have same names,we need to add extra parameter to distinguish
		String floorName = floor_tenant.substring(0,floor_tenant.indexOf("・"));
		Tenant tenant = this.tenantService.findByFloorNameAndBuildingId(tenantName,floorName,buildingName);
		Double areaRatio = tenant.getArea()/tenant.getFloor().getArea();
		return resolver.apply(buildingName, floorName, date, areaRatio);
	}
	private ReadingDTO findReadingAndConvertToTenantReadingDTO(String buildingName, String floorName, LocalDate date, Double areaRatio) {
		//find reading from DB for the floor that tenant occupies
		Readings readings = this.findFloorReading(floorName, buildingName, date);
		ReadingDTO readingDTO = ReadingMapper.mapToReadingDTO(readings);
		return ReadingMapper.floorToTenantReadingDTO(readingDTO, areaRatio);
	}
	
	@Override
	public Map<String, ReadingDTO> getTenantReadingsFromTempMap(String buildingName){

		List<String> floor_tenants = this.tenantService.getTenantListByBuildingName(buildingName);		
		//get a list of floor_tanant and iterate through each
		return floor_tenants.stream()
							.collect(Collectors.toMap
							(Function.identity(), 
							floor_tenant -> this.findReadingDTOFromFloor_Tenant(floor_tenant, buildingName, null,
																				this::findReadingDTOAndConvertToTenantReadingDTO),
							(existing, replacement) -> existing,
					        LinkedHashMap::new));
					 
	}
	private ReadingDTO findReadingDTOAndConvertToTenantReadingDTO(String buildingName, String floorName, LocalDate date, Double areaRatio) {
		//find reading from TempMap for the floor that tenant occupies
		ReadingDTO readingDTO = this.tempMapService.getReadingForFloor(buildingName, floorName)
									.orElseThrow(() -> new NullPointerException("Readings might not exist."));
		return ReadingMapper.floorToTenantReadingDTO(readingDTO, areaRatio);
	}

	@Override
	public Map<String, ReadingDTO> getFloorReadingsFromDb(String buildingName, LocalDate date) {
		
		Building building = this.buildingService.findByBuildingName(buildingName);
		List<Floor> floors = building.getFloors();
		Map<String,Readings> floorReadingsMap = floors.stream()
													  .collect(Collectors.toMap
													  (floor -> floor.getName(),
													   floor -> this.findFloorReading(floor.getName(), buildingName, date),
													  (existing, replacement) -> existing,
													   LinkedHashMap::new));
		return floorReadingsMap.entrySet().stream()
				  						  .collect(Collectors.toMap
				  						  (entry -> entry.getKey(),
										   entry -> ReadingMapper.mapToReadingDTO(entry.getValue()),
										  (existing, replacement) -> existing,
									       LinkedHashMap::new));
	}

	@Override
	public String updateReadings(String buildingName, LocalDate date, String floorName, ReadingDTO readingDTO) {
		
		Readings readings = getReadings(buildingName,date,floorName);
		
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
	
		String newComment = readingDTO.getComment();
		if(newComment!="" && newComment!=null) {
		
			readings.setComment(newComment);
		}
		else readings.setComment("");
		this.readingsRepository.save(readings);
		return "Readings updated successfully.";
	}
	@Override
	public String storeReadings(LinkedHashMap<String,ReadingDTO> floorMap, String buildingName,LocalDate date) {
		
		//needs to create a new record in reading date entity
		ReadingDate newReadingDate = this.readingDateService.createReadingDate(date,buildingName);
		
		for(Map.Entry<String, ReadingDTO> x : floorMap.entrySet()) {
			Building building = this.buildingService.findByBuildingName(buildingName);
			Floor floor = this.floorService.findFloorByNameAndBuildingId(x.getKey(),building.getId());
			
			Readings readings = ReadingMapper.mapToReadings(x.getValue(), floor, newReadingDate);
			
			this.readingsRepository.save(readings);
		}
		return "Readings stored successfully.";
	}

}
