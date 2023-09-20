package com.system.kenshinsystem.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.kenshinsystem.model.ReadingDate;

@Repository
public interface ReadingDateRepository extends JpaRepository<ReadingDate,Long>{
	
	Optional<ReadingDate> findByDate(LocalDate readingDate);
	List<ReadingDate> findByBuildingId(Long buildingId);

}
