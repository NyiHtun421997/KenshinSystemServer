package com.system.kenshinsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.kenshinsystem.model.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building,Long>{
	
	Optional<Building> findByName(String buildingName);

}
