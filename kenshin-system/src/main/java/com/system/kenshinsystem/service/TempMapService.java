package com.system.kenshinsystem.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.system.kenshinsystem.dto.ReadingDTO;

@Service
public class TempMapService {
	
    // Outer map: BuildingName -> Inner map: FloorName -> DTO Readings		
    private final Map<String, Map<String, ReadingDTO>> bld_floorMap = new LinkedHashMap<>();		
		
    public void addReading(ReadingDTO reading) {		
        // Check if the building name already exists in the outer map		
        String buildingName = reading.getBuildingName();		
        String floorName = reading.getFloorName();		
        if (!bld_floorMap.containsKey(buildingName)) {		
            // If not, create a new inner map for the building		
            bld_floorMap.put(buildingName, new LinkedHashMap<>());		
        }		
		
        // Get the inner map for the building		
        Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingName);		
		
        // Add or update the DTO reading for the floor		
        floorMap.put(floorName, reading);
        System.out.println(reading);
    }		
		
    public Map<String, Map<String, ReadingDTO>> getAllReadings() {		
        return bld_floorMap;		
    }		
		
    public Map<String, ReadingDTO> getReadingsForBuilding(String buildingName) {		
        return bld_floorMap.get(buildingName);		
    }		
		
    public ReadingDTO getReadingForFloor(String buildingName,String floorName) {		
        Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingName);		 
        if (floorMap != null) {		
            return floorMap.get(floorName);		
        }		
        return null;		
    }	
    
    public Boolean doesBuildingDataExist(String buildingName) {
    	
    	if(bld_floorMap.containsKey(buildingName)) return true;
    	
    	else return false;
    	
    }
		
		
}		

