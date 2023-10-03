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
			
		     // Create a Building instance and initialize its properties
		        Building buildingA = new Building();
		        buildingA.setName("Sample Building A");
		     // Create a Building instance and initialize its properties
		        Building buildingB = new Building();
		        buildingB.setName("Sample Building B");
		        
		        buildingRepository.save(buildingA);
		        buildingRepository.save(buildingB);
		        
		        ReadingDate readingDate1 = new ReadingDate();
		        readingDate1.setDate(LocalDate.of(2023, 8, 1));
		    
		        // Create a Building instance and initialize its properties
		           Building buildingC = new Building();
		           buildingC.setName("Sample Building C");
		        // Create a Building instance and initialize its properties
		           Building buildingD = new Building();
		           buildingD.setName("Sample Building D");
		           
		           buildingRepository.save(buildingC);
		           buildingRepository.save(buildingD);
		           
		           ReadingDate readingDate2 = new ReadingDate();
		           readingDate2.setDate(LocalDate.of(2023, 9, 1));
		           
		          readingDate1.setBuilding(List.of(buildingA,buildingB,buildingC));
			      readingDateRepository.save(readingDate1);
			      
		          readingDate2.setBuilding(List.of(buildingC,buildingD));
		         readingDateRepository.save(readingDate2);
	
		         ReadingDate readingDate3 = new ReadingDate();
		         readingDate3.setDate(LocalDate.of(2023, 10, 1));
		         readingDate3.setBuilding(List.of(buildingC));
		         
		         readingDateRepository.save(readingDate3);
		         
		         ReadingDate readingDate4 = new ReadingDate();
		         readingDate4.setDate(LocalDate.of(2022, 10, 1));
		         readingDate4.setBuilding(List.of(buildingC));
		         
		         readingDateRepository.save(readingDate4);
		         
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
		         reading1.setLightingReadingBeforeChange(0d);
		         reading1.setPowerReadingBeforeChange(0d);
		         reading1.setWaterReadingBeforeChange(0d);
		         reading1.setGasReadingBeforeChange(0d);
		         reading1.setFloor(floor1C);
		         reading1.setReadingDate(readingDate2);
		         readingsRepository.save(reading1);
		         
		         Readings reading2 = new Readings();
		         reading2.setLightingReading(22.3);
		         reading2.setPowerReading(37.5);
		         reading2.setWaterReading(12.4);
		         reading2.setGasReading(11d);
		         reading2.setLightingReadingBeforeChange(0d);
		         reading2.setPowerReadingBeforeChange(0d);
		         reading2.setWaterReadingBeforeChange(0d);
		         reading2.setGasReadingBeforeChange(0d);
		         reading2.setFloor(floor2C);
		         reading2.setReadingDate(readingDate3);
		         readingsRepository.save(reading2);
		         
		         Readings reading3 = new Readings();
		         reading3.setLightingReading(19.3);
		         reading3.setPowerReading(30.5);
		         reading3.setWaterReading(9.4);
		         reading3.setGasReading(11.3);
		         reading3.setLightingReadingBeforeChange(0d);
		         reading3.setPowerReadingBeforeChange(0d);
		         reading3.setWaterReadingBeforeChange(0d);
		         reading3.setGasReadingBeforeChange(0d);
		         reading3.setFloor(floor1B);
		         reading3.setReadingDate(readingDate1);
		         readingsRepository.save(reading3);
		         
		         Readings reading4 = new Readings();
		         reading4.setLightingReading(9.3);
		         reading4.setPowerReading(32.5);
		         reading4.setWaterReading(13.4);
		         reading4.setGasReading(11.9);
		         reading4.setLightingReadingBeforeChange(0d);
		         reading4.setPowerReadingBeforeChange(0d);
		         reading4.setWaterReadingBeforeChange(0d);
		         reading4.setGasReadingBeforeChange(0d);
		         reading4.setFloor(floor2B);
		         reading4.setReadingDate(readingDate1);
		         readingsRepository.save(reading4);
		         
		         Readings reading5 = new Readings();
		         reading5.setLightingReading(19.7);
		         reading5.setPowerReading(6.5);
		         reading5.setWaterReading(9.4);
		         reading5.setGasReading(21.3);
		         reading5.setLightingReadingBeforeChange(0d);
		         reading5.setPowerReadingBeforeChange(0d);
		         reading5.setWaterReadingBeforeChange(0d);
		         reading5.setGasReadingBeforeChange(0d);
		         reading5.setFloor(floor2C);
		         reading5.setReadingDate(readingDate2);
		         readingsRepository.save(reading5);
		         
		         Readings reading6 = new Readings();
		         reading6.setLightingReading(18.7);
		         reading6.setPowerReading(7.5);
		         reading6.setWaterReading(12.4);
		         reading6.setGasReading(10.3);
		         reading6.setLightingReadingBeforeChange(0d);
		         reading6.setPowerReadingBeforeChange(0d);
		         reading6.setWaterReadingBeforeChange(0d);
		         reading6.setGasReadingBeforeChange(0d);
		         reading6.setFloor(floor3C);
		         reading6.setReadingDate(readingDate2);
		         readingsRepository.save(reading6);
		         
		         Readings reading7 = new Readings();
		         reading7.setLightingReading(14.7);
		         reading7.setPowerReading(13.5);
		         reading7.setWaterReading(10.9);
		         reading7.setGasReading(9.6);
		         reading7.setLightingReadingBeforeChange(0d);
		         reading7.setPowerReadingBeforeChange(0d);
		         reading7.setWaterReadingBeforeChange(0d);
		         reading7.setGasReadingBeforeChange(0d);
		         reading7.setFloor(floor1C);
		         reading7.setReadingDate(readingDate1);
		         readingsRepository.save(reading7);
		         
		         Readings reading8 = new Readings();
		         reading8.setLightingReading(17.7);
		         reading8.setPowerReading(11.5);
		         reading8.setWaterReading(11.4);
		         reading8.setGasReading(8.3);
		         reading8.setLightingReadingBeforeChange(0d);
		         reading8.setPowerReadingBeforeChange(0d);
		         reading8.setWaterReadingBeforeChange(0d);
		         reading8.setGasReadingBeforeChange(0d);
		         reading8.setFloor(floor3C);
		         reading8.setReadingDate(readingDate1);
		         readingsRepository.save(reading8);
		         
		         Readings reading9 = new Readings();
		         reading9.setLightingReading(16.1);
		         reading9.setPowerReading(10.3);
		         reading9.setWaterReading(10.4);
		         reading9.setGasReading(9.9);
		         reading9.setLightingReadingBeforeChange(0d);
		         reading9.setPowerReadingBeforeChange(0d);
		         reading9.setWaterReadingBeforeChange(0d);
		         reading9.setGasReadingBeforeChange(0d);
		         reading9.setFloor(floor1C);
		         reading9.setReadingDate(readingDate3);
		         readingsRepository.save(reading9);
		         
		         Readings reading10 = new Readings();
		         reading10.setLightingReading(17.7);
		         reading10.setPowerReading(12.5);
		         reading10.setWaterReading(11.4);
		         reading10.setGasReading(10.3);
		         reading10.setLightingReadingBeforeChange(0d);
		         reading10.setPowerReadingBeforeChange(0d);
		         reading10.setWaterReadingBeforeChange(0d);
		         reading10.setGasReadingBeforeChange(0d);
		         reading10.setFloor(floor3C);
		         reading10.setReadingDate(readingDate3);
		         readingsRepository.save(reading10);
		         
		      // Reading 11
		         Readings reading11 = new Readings();
		         reading11.setLightingReading(20.2);  // Random value within ±3 fluctuation
		         reading11.setPowerReading(15.1);     // Random value within ±3 fluctuation
		         reading11.setWaterReading(13.9);     // Random value within ±3 fluctuation
		         reading11.setGasReading(11.7);       // Random value within ±3 fluctuation
		         reading11.setLightingReadingBeforeChange(0d);
		         reading11.setPowerReadingBeforeChange(0d);
		         reading11.setWaterReadingBeforeChange(0d);
		         reading11.setGasReadingBeforeChange(0d);
		         reading11.setFloor(floor1C);
		         reading11.setReadingDate(readingDate4);
		         readingsRepository.save(reading11);

		         // Reading 12
		         Readings reading12 = new Readings();
		         reading12.setLightingReading(16.8);  // Random value within ±3 fluctuation
		         reading12.setPowerReading(14.2);     // Random value within ±3 fluctuation
		         reading12.setWaterReading(10.7);     // Random value within ±3 fluctuation
		         reading12.setGasReading(12.1);       // Random value within ±3 fluctuation
		         reading12.setLightingReadingBeforeChange(0d);
		         reading12.setPowerReadingBeforeChange(0d);
		         reading12.setWaterReadingBeforeChange(0d);
		         reading12.setGasReadingBeforeChange(0d);
		         reading12.setFloor(floor2C);
		         reading12.setReadingDate(readingDate4);
		         readingsRepository.save(reading12);

		         // Reading 13
		         Readings reading13 = new Readings();
		         reading13.setLightingReading(18.6);  // Random value within ±3 fluctuation
		         reading13.setPowerReading(11.8);     // Random value within ±3 fluctuation
		         reading13.setWaterReading(14.3);     // Random value within ±3 fluctuation
		         reading13.setGasReading(9.9);        // Random value within ±3 fluctuation
		         reading13.setLightingReadingBeforeChange(0d);
		         reading13.setPowerReadingBeforeChange(0d);
		         reading13.setWaterReadingBeforeChange(0d);
		         reading13.setGasReadingBeforeChange(0d);
		         reading13.setFloor(floor3C);
		         reading13.setReadingDate(readingDate4);
		         readingsRepository.save(reading13);
		         
		      // Reading 14
		         Readings reading14 = new Readings();
		         reading14.setLightingReading(19.3);  // Random value within ±3 fluctuation
		         reading14.setPowerReading(13.7);     // Random value within ±3 fluctuation
		         reading14.setWaterReading(10.2);     // Random value within ±3 fluctuation
		         reading14.setGasReading(12.8);       // Random value within ±3 fluctuation
		         reading14.setLightingReadingBeforeChange(0d);
		         reading14.setPowerReadingBeforeChange(0d);
		         reading14.setWaterReadingBeforeChange(0d);
		         reading14.setGasReadingBeforeChange(0d);
		         reading14.setFloor(floor2C);
		         reading14.setReadingDate(readingDate1);
		         readingsRepository.save(reading14);

			
		};
	}

}
