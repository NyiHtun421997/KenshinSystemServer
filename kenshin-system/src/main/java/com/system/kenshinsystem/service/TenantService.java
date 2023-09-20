package com.system.kenshinsystem.service;

import java.util.List;
import com.system.kenshinsystem.model.Tenant;

public interface TenantService {
	
	List<Tenant> getTenantListByBuildingNameAndFloorName(String buildingName,String floorName);

}
