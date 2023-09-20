package com.system.kenshinsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.model.Floor;
import com.system.kenshinsystem.model.Tenant;
import com.system.kenshinsystem.repository.TenantRepository;

@Service
public class TenantServiceImpl implements TenantService{
	
	private final FloorService floorService;
	private final BuildingService buildingService;
	private final TenantRepository tenantRepository;
	@Autowired
	public TenantServiceImpl(FloorService floorService,BuildingService buildingService,TenantRepository tenantRepository) {
		
		this.floorService = floorService;
		this.buildingService = buildingService;
		this.tenantRepository = tenantRepository;
	}
	
	@Override
	public List<Tenant> getTenantListByBuildingNameAndFloorName(String buildingName,String floorName){
		
		Building building = this.buildingService.findByBuildingName(buildingName);
		Floor floor = this.floorService.findFloorByNameAndBuildingId(floorName, building.getId());
		
		return this.tenantRepository.findByFloorId(floor.getId());
	}

}
