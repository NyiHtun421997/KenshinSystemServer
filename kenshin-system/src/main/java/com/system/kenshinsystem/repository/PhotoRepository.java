package com.system.kenshinsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.kenshinsystem.model.Photos;

@Repository
public interface PhotoRepository extends JpaRepository<Photos,Long>{
	
	

}
