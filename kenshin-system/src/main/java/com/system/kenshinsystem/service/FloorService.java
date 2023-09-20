package com.system.kenshinsystem.service;

import java.util.List;


import com.system.kenshinsystem.model.Floor;

public interface FloorService {
	
	Floor findFloorByNameAndBuildingId(String name,Long buildingId);
	List<Floor> getFloorListByBuildingName(String buildingName);
	

}
