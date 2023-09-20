package com.system.kenshinsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.kenshinsystem.model.Floor;

public interface FloorRepository extends JpaRepository<Floor,Long>{
	
	Optional<Floor> findByNameAndBuildingId(String buildingName,Long buildingId);
	List<Floor> findByBuildingId(Long buildingId);

}
