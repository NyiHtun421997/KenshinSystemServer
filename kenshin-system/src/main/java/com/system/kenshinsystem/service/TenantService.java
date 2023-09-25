package com.system.kenshinsystem.service;

import java.util.List;

public interface TenantService {
	
	List<String> getTenantListByBuildingName(String buildingName);

}
