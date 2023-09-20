package com.system.kenshinsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.kenshinsystem.model.Readings;

public interface ReadingsRepository extends JpaRepository<Readings,Long>{
	
	Readings findByFloorIdAndReadingDateId(Long floorId,Long readingDateId);

}
