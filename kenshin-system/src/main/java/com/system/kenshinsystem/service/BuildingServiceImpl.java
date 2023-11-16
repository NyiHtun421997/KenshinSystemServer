package com.system.kenshinsystem.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.repository.BuildingRepository;

@Service
public class BuildingServiceImpl implements BuildingService{
	
	private final BuildingRepository buildingRepository;
	
	@Autowired
	public BuildingServiceImpl(BuildingRepository buildingRepository) {

		this.buildingRepository = buildingRepository;
	}

	@Override
	public List<Building> getBuildingNames() {
		
		return this.buildingRepository.findAll();
		
	}
	
	@Override
	public Building findByBuildingName(String buildingName) {
		
		return this.buildingRepository.findByName(buildingName)
					.orElseThrow(() -> new NullPointerException("Building not found."));	
	}

}
