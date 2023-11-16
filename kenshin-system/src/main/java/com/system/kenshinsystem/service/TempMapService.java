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
		
    public void addReading(ReadingDTO readingDTO) {		
        // Check if the building name already exists in the outer map		
        String buildingName = readingDTO.getBuildingName();		
        String floorName = readingDTO.getFloorName();
        BuildingKey buildingKey = new BuildingKey(buildingName);
        if (!bld_floorMap.containsKey(buildingKey)) {		
            // If not, create a new inner map for the building		
            bld_floorMap.put(buildingKey, new LinkedHashMap<>());		
        }		
		
        // Get the inner map for the building		
        Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingKey);		
		
        // Add or update the DTO reading for the floor		
        floorMap.put(floorName, readingDTO);
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
    		
    		String floor = x.getKey(); // this will be 1F・TenantA format		
    		floor = floor.substring(0,floor.indexOf("・"));
    		if(x.getKey()!= null && x.getValue()!=null && x.getValue()!="") {
    			if(commentDataForFloor.containsKey(floor)) {    			
        			//for tenants of the same floor
            		String existingCmt = commentDataForFloor.get(floor);
            		String newCmt = existingCmt+"\n***************\n【"+x.getKey()+"】\n"+x.getValue()+"\n";	
/*                   Key:1F -> Value:【1F・TenantA】
            							comment 
            						***************
            						 【1F・TenantB】
            						    comment                                                                      */
            		commentDataForFloor.put(floor, newCmt);
        		}
        		else { 
/*                   Key:1F -> Value:【1F・TenantA】
        								comment                                                                      */
        			commentDataForFloor.put(floor, "【"+x.getKey()+"】\n"+x.getValue());
        		}	
    		}   		
    	}
    	//store each comments inside Map to each readingDTO obj
    	// Get the inner map for the building		
    	BuildingKey buildingKey = new BuildingKey(buildingName);
        Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingKey);
        
        for(String floorName : floorMap.keySet()) {
        	String originalComment = floorMap.get(floorName).getComment();
        	String newComment = commentDataForFloor.get(floorName);
        	if(newComment!=null) {
        		originalComment = originalComment + "\n***************\n"+newComment;
            	floorMap.get(floorName).setComment(originalComment);
        	}	
        }
    }
    //method to approve readings stored in temporary map
    public  LinkedHashMap<String, ReadingDTO> finalApproveReadings(String buildingName) {
    	// Get the inner map for the building		
    	BuildingKey buildingKey = new BuildingKey(buildingName);
    	for(BuildingKey x :  bld_floorMap.keySet()) {
    		if(x.equals(buildingKey))
    		buildingKey = x; break;
    	}
    	//check the boolean fields of buildingKey obj
    	if(buildingKey.getFirstChecked()) {
    		if(buildingKey.getSecondChecked()) {
    			buildingKey.setApproved(true);
    		}
    		else 
    			buildingKey.setSecondChecked(true);
    	}
    	else
    		buildingKey.setFirstChecked(true);
    	Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingKey);
    	bld_floorMap.replace(buildingKey, floorMap);
    	
    	if(buildingKey.getFirstChecked() == true && buildingKey.getSecondChecked() == true && buildingKey.getApproved() == true) {
    		//clearing the reading data from TempMap
    		bld_floorMap.remove(buildingKey, floorMap);
    		return (LinkedHashMap<String, ReadingDTO>) floorMap;
    	}
    	else return null; 	
    }
	public void approveReadings(String buildingName) {
		// Get the inner map for the building		
    	BuildingKey buildingKey = new BuildingKey(buildingName);
    	for(BuildingKey x :  bld_floorMap.keySet()) {
    		if(x.equals(buildingKey))
    		buildingKey = x; break;
    	}
    	//check the boolean fields of buildingKey obj
    	if(buildingKey.getFirstChecked()) 
    		buildingKey.setSecondChecked(true);
    	else
    		buildingKey.setFirstChecked(true);
    	Map<String, ReadingDTO> floorMap = bld_floorMap.get(buildingKey);
    	bld_floorMap.replace(buildingKey, floorMap);
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

