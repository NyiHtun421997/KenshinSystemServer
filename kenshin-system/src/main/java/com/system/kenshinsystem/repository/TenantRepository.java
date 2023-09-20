package com.system.kenshinsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.kenshinsystem.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant,Long>{
	
	List<Tenant>findByFloorId(Long Id);

}
