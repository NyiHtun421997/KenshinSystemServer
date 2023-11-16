package com.system.kenshinsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.kenshinsystem.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant,Long>{
	
	Optional<List<Tenant>> findByFloorId(Long Id);

	Optional<Tenant> findByNameAndFloorId(String tenantName,Long floor_id);

}
