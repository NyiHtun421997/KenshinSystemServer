package com.system.kenshinsystem.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.system.kenshinsystem.dto.ReadingDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Service
public class TempMapService {
	
    // Outer map: BuildingName -> Inner map: FloorName -> DTO Readings		
    private final Map<BuildingKey, Map<String, ReadingDTO>> bld_floorMap;
    
    public TempMapService() {
    	bld_floorMap = new LinkedHashMap<>();
    }
		
    public void addReading(ReadingDTO reading) {		
        // Check if the building name already exists in the outer map		
        String buildingName = reading.getBuildingName();		
        String floorName = reading.getFloorName();
        BuildingKey buildingKey = new BuildingKey(buildingName);
        if (!bld_floorMap.containsKey(buildingKey)) {		
            // If not, create a new inner map for the building		
            bld_floorMap.put(buildingKey, new LinkedHashMap<>());		
        }		
		
        // Get the inner map for the building		
        Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingKey);		
		
        // Add or update the DTO reading for the floor		
        floorMap.put(floorName, reading);
        System.out.println(reading);
    }		
		
    public Map<String, Map<String, ReadingDTO>> getAllReadings() {	
    	Map<String, Map<String, ReadingDTO>> newBld_FloorMap = new LinkedHashMap<>();
    	for(BuildingKey x: bld_floorMap.keySet()) {
    		newBld_FloorMap.put(x.getBuildingName(), bld_floorMap.get(x));
    	}
        return 	newBld_FloorMap;
    }		
		
    public Map<String, ReadingDTO> getReadingsForBuilding(String buildingName) {		
        BuildingKey buildingKey = new BuildingKey(buildingName);
    	return bld_floorMap.get(buildingKey);		
    }		
		
    public ReadingDTO getReadingForFloor(String buildingName,String floorName) {
    	BuildingKey buildingKey = new BuildingKey(buildingName);
        Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingKey);		 
        if (floorMap != null) {		
            return floorMap.get(floorName);		
        }		
        return null;		
    }	
    
    public Boolean doesBuildingDataExist(String buildingName) {
    	BuildingKey buildingKey = new BuildingKey(buildingName);
    	if(bld_floorMap.containsKey(buildingKey)) return true;
    	
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
    			String newCmt = existingCmt+"【"+x.getKey()+"】\n"+x.getValue()+"\n";
    			System.out.println(newCmt);
    			
    			commentDataForFloor.put(floor, newCmt);
    		}
    		else {
    			commentDataForFloor.put(floor, "【"+x.getKey()+"】\n"+x.getValue()+"\n");
    		}
    	}
    	//store each comments inside Map to each readingDTO obj
    	// Get the inner map for the building		
    	BuildingKey buildingKey = new BuildingKey(buildingName);
        Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingKey);
        
        for(String x : floorMap.keySet()) {
        	
        	floorMap.get(x).setComment(commentDataForFloor.get(x));
        }
    }
		
		
}
//Building Class that will serve as a key value for outer map
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class BuildingKey{
	private String buildingName;
	private Boolean firstChecked = false;
	private Boolean secondChecked = false;
	private Boolean approved = false;
	
	BuildingKey(String buildingName){
		this.buildingName = buildingName;
	}
	//Must override equals method and hash code so that object of the same buildingName could have the same value
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(this == o) return true;
		if((o instanceof BuildingKey) && ((BuildingKey)o).getBuildingName().equals(this.buildingName)  ) {
			return true;
		}
		else return false;
	}
	@Override
	public int hashCode() {
		return Objects.hash(buildingName);
	}
}

