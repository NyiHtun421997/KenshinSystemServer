package com.system.kenshinsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.kenshinsystem.model.Readings;

public interface ReadingsRepository extends JpaRepository<Readings,Long>{
	
	Optional<Readings> findByFloorIdAndReadingDateId(Long floorId,Long readingDateId);

}
