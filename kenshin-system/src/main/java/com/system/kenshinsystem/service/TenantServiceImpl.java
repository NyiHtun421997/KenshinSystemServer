package com.system.kenshinsystem.service;

import java.util.ArrayList;
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
	public List<String> getTenantListByBuildingName(String buildingName) {
		
		List<String> tenantsToReturn = new ArrayList<>();
		List<Floor> floors = this.floorService.getFloorListByBuildingName(buildingName);
		for(Floor floor : floors) {
			List<Tenant> tenants = this.tenantRepository.findByFloorId(floor.getId());
			for(Tenant x: tenants) {
				tenantsToReturn.add(floor.getName() + "・" + x.getName());
			}
			
		}
		return tenantsToReturn;
	}
	@Override
	public Double getAreaRatio(String tenantName) {
		
		Tenant tenant = this.tenantRepository.findByName(tenantName);
		Floor floor = tenant.getFloor();
		return tenant.getArea()/floor.getArea();
	}
	
	@Override
	public Tenant findByTenantName(String tenantName) {
		
		return this.tenantRepository.findByName(tenantName);
	}

}
