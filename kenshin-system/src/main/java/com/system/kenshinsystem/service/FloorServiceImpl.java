package com.system.kenshinsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.model.Floor;
import com.system.kenshinsystem.repository.FloorRepository;

@Service
public class FloorServiceImpl implements FloorService{
	
	private final FloorRepository floorRepository;
	private final BuildingService buildingService;
	
	@Autowired
	public FloorServiceImpl(FloorRepository floorRepository,BuildingService buildingService) {
		
		this.floorRepository = floorRepository;
		this.buildingService = buildingService;
	}

	@Override
	public Floor findFloorByNameAndBuildingId(String name, Long buildingId) {
		
		Optional<Floor> floorOptional = this.floorRepository.findByNameAndBuildingId(name, buildingId);
		if(floorOptional.isPresent()) {
			
			return floorOptional.get();
		}
		else return null;
	}

	@Override
	public List<Floor> getFloorListByBuildingName(String buldingName) {
		
		Building building = this.buildingService.findByBuildingName(buldingName);
		return this.floorRepository.findByBuildingId(building.getId());
		
	}

}
