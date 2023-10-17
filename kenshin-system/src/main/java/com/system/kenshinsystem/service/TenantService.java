package com.system.kenshinsystem.service;

import java.util.List;

import com.system.kenshinsystem.model.Tenant;

public interface TenantService {
	
	List<String> getTenantListByBuildingName(String buildingName);
	
	Double getAreaRatio(String tenantName,String floorName,String buildingName);
	
	Tenant findByFloorNameAndBuildingId(String tenantName,String floorName,String buildingName);

}
