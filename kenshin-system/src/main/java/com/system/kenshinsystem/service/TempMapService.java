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
    
    public void storeComments(String buildingName, LinkedHashMap<String,String> commentData) {
    	
    	LinkedHashMap<String,String> commentDataForFloor = new LinkedHashMap<>();
    	//converting comments for each tenant to comments for each floor
    	//comments of tenants in the same floor are combined
    	
    	for(Map.Entry<String, String> x : commentData.entrySet()) {
    		
    		//Testing
    		System.out.println(x.getKey()+" "+x.getValue());
    		String floor = x.getKey(); // this will be floor・tenant format
    		floor = floor.substring(0,floor.indexOf("・"));
    		System.out.println(floor);
    		if(commentDataForFloor.containsKey(floor)) {
    			//for tenants of the same floor
    			String existingCmt = commentDataForFloor.get(floor);
    			System.out.println(existingCmt);
    			String newCmt = existingCmt+"\n"+x.getKey()+"\n"+x.getValue();
    			System.out.println(newCmt);
    			
    			commentDataForFloor.put(floor, newCmt);
    		}
    		else {
    			commentDataForFloor.put(floor, x.getKey()+"\n"+x.getValue());
    		}
    	}
    	//store each comments inside Map to each readingDTO obj
    	// Get the inner map for the building		
        Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingName);
        
        for(String x : floorMap.keySet()) {
        	
        	floorMap.get(x).setComment(commentDataForFloor.get(x));
        }
    }
		
		
}		

