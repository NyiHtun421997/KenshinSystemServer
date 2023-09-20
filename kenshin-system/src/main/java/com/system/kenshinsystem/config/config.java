package com.system.kenshinsystem.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.system.kenshinsystem.model.Building;
import com.system.kenshinsystem.model.Floor;
import com.system.kenshinsystem.model.ReadingDate;
import com.system.kenshinsystem.model.Readings;
import com.system.kenshinsystem.model.Tenant;
import com.system.kenshinsystem.repository.BuildingRepository;
import com.system.kenshinsystem.repository.FloorRepository;
import com.system.kenshinsystem.repository.ReadingDateRepository;
import com.system.kenshinsystem.repository.ReadingsRepository;
import com.system.kenshinsystem.repository.TenantRepository;


@Configuration
public class config {
	
	@Bean
	CommandLineRunner commandLineRunner(BuildingRepository buildingRepository,ReadingDateRepository readingDateRepository
			,FloorRepository floorRepository,TenantRepository tenantRepository,ReadingsRepository readingsRepository) {
		return args ->{
			List<Building> buildings1 = new ArrayList<>();
		     // Create a Building instance and initialize its properties
		        Building buildingA = new Building();
		        buildingA.setName("Sample Building A");
		     // Create a Building instance and initialize its properties
		        Building buildingB = new Building();
		        buildingB.setName("Sample Building B");
		        buildings1.add(buildingA);
		        buildings1.add(buildingB);
		        buildingRepository.save(buildingA);
		        buildingRepository.save(buildingB);
		        
		        ReadingDate readingDate1 = new ReadingDate();
		        readingDate1.setDate(LocalDate.of(2023, 9, 1));
		        readingDate1.setBuilding(buildings1);
		        
		        readingDateRepository.save(readingDate1);
		        
		        
		        List<Building> buildings2 = new ArrayList<>();
		        // Create a Building instance and initialize its properties
		           Building buildingC = new Building();
		           buildingC.setName("Sample Building C");
		        // Create a Building instance and initialize its properties
		           Building buildingD = new Building();
		           buildingD.setName("Sample Building D");
		           buildings2.add(buildingC);
		           buildings2.add(buildingD);
		           buildingRepository.save(buildingC);
		           buildingRepository.save(buildingD);
		           
		           ReadingDate readingDate2 = new ReadingDate();
		           readingDate2.setDate(LocalDate.of(2023, 9, 2));
		           readingDate2.setBuilding(buildings2);
		           
		         readingDateRepository.save(readingDate2);
		         
		         List<Building> buildings3 = new ArrayList<>();
		         buildings3.add(buildingC);
		         ReadingDate readingDate3 = new ReadingDate();
		         readingDate3.setDate(LocalDate.of(2023, 10, 3));
		         readingDate3.setBuilding(buildings3);
		         
		         readingDateRepository.save(readingDate3);
		         
		         //creating a new Floor record
		         
		         String buildingNameC = "Sample Building C";
		         buildingC = buildingRepository.findByName(buildingNameC).get();
		    
		         Floor floor1C = new Floor();
		         floor1C.setName("1F");
		         floor1C.setArea(100d);
		         floor1C.setBuilding(buildingC);
		         floorRepository.save(floor1C);
		         
		         Floor floor2C = new Floor();
		         floor2C.setName("2F");
		         floor2C.setArea(100d);
		         floor2C.setBuilding(buildingC);
		         floorRepository.save(floor2C);
		         
		         Floor floor3C = new Floor();
		         floor3C.setName("3F");
		         floor3C.setArea(100d);
		         floor3C.setBuilding(buildingC);
		         floorRepository.save(floor3C);
		         
		         buildingC.setFloors(List.of(floor1C,floor2C,floor3C));
		         buildingRepository.save(buildingC);
		         
		         //creating a new Floor record
		         
		         String buildingNameB = "Sample Building B";
		         buildingB = buildingRepository.findByName(buildingNameB).get();
		    
		         Floor floor1B = new Floor();
		         floor1B.setName("1F");
		         floor1B.setArea(80d);
		         floor1B.setBuilding(buildingB);
		         floorRepository.save(floor1B);
		         
		         Floor floor2B = new Floor();
		         floor2B.setName("2F");
		         floor2B.setArea(80d);
		         floor2B.setBuilding(buildingB);
		         floorRepository.save(floor2B);
		         
		         Floor floor3B = new Floor();
		         floor3B.setName("3F");
		         floor3B.setArea(80d);
		         floor3B.setBuilding(buildingB);
		         floorRepository.save(floor3B);
		         
		         buildingB.setFloors(List.of(floor1B,floor2B,floor3B));
		         buildingRepository.save(buildingB);
		         
		         //creating tenant records
		         Tenant t11FC = new Tenant();
		         t11FC.setArea(50d);
		         t11FC.setName("Dental");
		         t11FC.setFloor(floor1C);
		         
		         Tenant t21FC = new Tenant();
		         t21FC.setArea(50d);
		         t21FC.setName("Convinienece");
		         t21FC.setFloor(floor1C);
		         
		         
		         Tenant t32FC = new Tenant();
		         t32FC.setArea(100d);
		         t32FC.setName("ABC＿CompanyLimited");
		         t32FC.setFloor(floor2C);
		         tenantRepository.saveAll(List.of(t11FC,t21FC,t32FC));
		         
		         //creating reading record
		         Readings reading1 = new Readings();
		         reading1.setLightingReading(20.3);
		         reading1.setPowerReading(34.5);
		         reading1.setWaterReading(12.2);
		         reading1.setGasReading(12d);
		         reading1.setFloor(floor1C);
		         reading1.setReadingDate(readingDate2);
		         readingsRepository.save(reading1);
		         
		         Readings reading2 = new Readings();
		         reading2.setLightingReading(22.3);
		         reading2.setPowerReading(37.5);
		         reading2.setWaterReading(12.4);
		         reading2.setGasReading(11d);
		         reading2.setFloor(floor2C);
		         reading2.setReadingDate(readingDate3);
		         readingsRepository.save(reading2);
		         
		         Readings reading3 = new Readings();
		         reading3.setLightingReading(19.3);
		         reading3.setPowerReading(30.5);
		         reading3.setWaterReading(9.4);
		         reading3.setGasReading(11.3);
		         reading3.setFloor(floor1B);
		         reading3.setReadingDate(readingDate1);
		         readingsRepository.save(reading3);
		         
		         Readings reading4 = new Readings();
		         reading4.setLightingReading(9.3);
		         reading4.setPowerReading(32.5);
		         reading4.setWaterReading(13.4);
		         reading4.setGasReading(11.9);
		         reading4.setFloor(floor2B);
		         reading4.setReadingDate(readingDate1);
		         readingsRepository.save(reading4);
		         
		         Readings reading5 = new Readings();
		         reading5.setLightingReading(19.7);
		         reading5.setPowerReading(6.5);
		         reading5.setWaterReading(9.4);
		         reading5.setGasReading(21.3);
		         reading5.setFloor(floor3B);
		         reading5.setReadingDate(readingDate1);
		         readingsRepository.save(reading5);
		         
		         
			
		};
	}

}
