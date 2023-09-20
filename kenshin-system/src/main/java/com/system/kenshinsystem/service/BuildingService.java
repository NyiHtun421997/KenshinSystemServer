package com.system.kenshinsystem.service;

import java.util.List;

import com.system.kenshinsystem.model.Building;

public interface BuildingService {
	
	List<Building> getBuildingNames();
	Building findByBuildingName(String buildingName);

}
