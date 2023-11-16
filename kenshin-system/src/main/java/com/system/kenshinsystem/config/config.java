package com.system.kenshinsystem.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

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
import com.system.kenshinsystem.security.users.Role;
import com.system.kenshinsystem.security.users.User;
import com.system.kenshinsystem.security.users.UserRepository;

import lombok.RequiredArgsConstructor;


@Configuration
@Profile("test")
@RequiredArgsConstructor
public class config {
	
	private final PasswordEncoder encoder;
	@Bean
	CommandLineRunner commandLineRunner(BuildingRepository buildingRepository,ReadingDateRepository readingDateRepository
			,FloorRepository floorRepository,TenantRepository tenantRepository,ReadingsRepository readingsRepository,UserRepository userRepo) {
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
		         readingDate4.setDate(LocalDate.of(2022, 11, 1));
		         readingDate4.setBuilding(List.of(buildingC));
		         
		         readingDateRepository.save(readingDate4);
		         
		         ReadingDate readingDate5 = new ReadingDate();
		         readingDate5.setDate(LocalDate.of(2022, 10, 1));
		         readingDate5.setBuilding(List.of(buildingC));
		         
		         readingDateRepository.save(readingDate5);
		         
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
		         
		         Tenant t43FC = new Tenant();
		         t43FC.setArea(100d);
		         t43FC.setName("大京");
		         t43FC.setFloor(floor3C);
		         tenantRepository.saveAll(List.of(t11FC,t21FC,t32FC,t43FC));
		         
		         //creating reading record
		         
		         Readings reading1 = new Readings();
		         reading1.setLightingReading(9.3);
		         reading1.setPowerReading(32.5);
		         reading1.setWaterReading(13.4);
		         reading1.setGasReading(11.9);
		         reading1.setLightingReadingBeforeChange(0d);
		         reading1.setPowerReadingBeforeChange(0d);
		         reading1.setWaterReadingBeforeChange(0d);
		         reading1.setGasReadingBeforeChange(0d);
		         reading1.setFloor(floor2B);
		         reading1.setReadingDate(readingDate1);
		         readingsRepository.save(reading1);
		         
		         Readings reading2 = new Readings();
		         reading2.setLightingReading(19.7);
		         reading2.setPowerReading(6.5);
		         reading2.setWaterReading(9.4);
		         reading2.setGasReading(21.3);
		         reading2.setLightingReadingBeforeChange(0d);
		         reading2.setPowerReadingBeforeChange(0d);
		         reading2.setWaterReadingBeforeChange(0d);
		         reading2.setGasReadingBeforeChange(0d);
		         reading2.setFloor(floor1B);
		         reading2.setReadingDate(readingDate2);
		         readingsRepository.save(reading2);
		         
		      // Reading 3
		         Readings reading3 = new Readings();
		         reading3.setLightingReading(27.3);
		         reading3.setPowerReading(27.5);
		         reading3.setWaterReading(19.2);
		         reading3.setGasReading(19.0);
		         reading3.setLightingReadingBeforeChange(0d);
		         reading3.setPowerReadingBeforeChange(0d);
		         reading3.setWaterReadingBeforeChange(0d);
		         reading3.setGasReadingBeforeChange(0d);
		         reading3.setFloor(floor1C);
		         reading3.setReadingDate(readingDate5);
		         readingsRepository.save(reading3);

		         // Reading 4
		         Readings reading4 = new Readings();
		         reading4.setLightingReading(34.3);
		         reading4.setPowerReading(34.5);
		         reading4.setWaterReading(26.2);
		         reading4.setGasReading(26.0);
		         reading4.setLightingReadingBeforeChange(0d);
		         reading4.setPowerReadingBeforeChange(0d);
		         reading4.setWaterReadingBeforeChange(0d);
		         reading4.setGasReadingBeforeChange(0d);
		         reading4.setFloor(floor2C);
		         reading4.setReadingDate(readingDate5);
		         readingsRepository.save(reading4);

		         // Reading 5
		         Readings reading5 = new Readings();
		         reading5.setLightingReading(41.3);
		         reading5.setPowerReading(41.5);
		         reading5.setWaterReading(33.2);
		         reading5.setGasReading(33.0);
		         reading5.setLightingReadingBeforeChange(0d);
		         reading5.setPowerReadingBeforeChange(0d);
		         reading5.setWaterReadingBeforeChange(0d);
		         reading5.setGasReadingBeforeChange(0d);
		         reading5.setFloor(floor3C);
		         reading5.setReadingDate(readingDate5);
		         readingsRepository.save(reading5);

		         // Reading 6
		         Readings reading6 = new Readings();
		         reading6.setLightingReading(48.3);
		         reading6.setPowerReading(48.5);
		         reading6.setWaterReading(40.2);
		         reading6.setGasReading(40.0);
		         reading6.setLightingReadingBeforeChange(0d);
		         reading6.setPowerReadingBeforeChange(0d);
		         reading6.setWaterReadingBeforeChange(0d);
		         reading6.setGasReadingBeforeChange(0d);
		         reading6.setFloor(floor1C);
		         reading6.setReadingDate(readingDate4);
		         readingsRepository.save(reading6);

		         // Reading 7
		         Readings reading7 = new Readings();
		         reading7.setLightingReading(55.3);
		         reading7.setPowerReading(55.5);
		         reading7.setWaterReading(47.2);
		         reading7.setGasReading(47.0);
		         reading7.setLightingReadingBeforeChange(0d);
		         reading7.setPowerReadingBeforeChange(0d);
		         reading7.setWaterReadingBeforeChange(0d);
		         reading7.setGasReadingBeforeChange(0d);
		         reading7.setFloor(floor2C);
		         reading7.setReadingDate(readingDate4);
		         readingsRepository.save(reading7);

		         // Reading 8
		         Readings reading8 = new Readings();
		         reading8.setLightingReading(62.3);
		         reading8.setPowerReading(62.5);
		         reading8.setWaterReading(54.2);
		         reading8.setGasReading(54.0);
		         reading8.setLightingReadingBeforeChange(0d);
		         reading8.setPowerReadingBeforeChange(0d);
		         reading8.setWaterReadingBeforeChange(0d);
		         reading8.setGasReadingBeforeChange(0d);
		         reading8.setFloor(floor3C);
		         reading8.setReadingDate(readingDate4);
		         readingsRepository.save(reading8);

		         // Reading 9
		         Readings reading9 = new Readings();
		         reading9.setLightingReading(69.3);
		         reading9.setPowerReading(69.5);
		         reading9.setWaterReading(61.2);
		         reading9.setGasReading(61.0);
		         reading9.setLightingReadingBeforeChange(0d);
		         reading9.setPowerReadingBeforeChange(0d);
		         reading9.setWaterReadingBeforeChange(0d);
		         reading9.setGasReadingBeforeChange(0d);
		         reading9.setFloor(floor1C);
		         reading9.setReadingDate(readingDate1);
		         readingsRepository.save(reading9);

		         // Reading 10
		         Readings reading10 = new Readings();
		         reading10.setLightingReading(76.3);
		         reading10.setPowerReading(76.5);
		         reading10.setWaterReading(68.2);
		         reading10.setGasReading(68.0);
		         reading10.setLightingReadingBeforeChange(0d);
		         reading10.setPowerReadingBeforeChange(0d);
		         reading10.setWaterReadingBeforeChange(0d);
		         reading10.setGasReadingBeforeChange(0d);
		         reading10.setFloor(floor2C);
		         reading10.setReadingDate(readingDate1);
		         readingsRepository.save(reading10);

		         // Reading 11
		         Readings reading11 = new Readings();
		         reading11.setLightingReading(83.3);
		         reading11.setPowerReading(83.5);
		         reading11.setWaterReading(75.2);
		         reading11.setGasReading(75.0);
		         reading11.setLightingReadingBeforeChange(0d);
		         reading11.setPowerReadingBeforeChange(0d);
		         reading11.setWaterReadingBeforeChange(0d);
		         reading11.setGasReadingBeforeChange(0d);
		         reading11.setFloor(floor3C);
		         reading11.setReadingDate(readingDate1);
		         readingsRepository.save(reading11);

		         // Reading 12
		         Readings reading12 = new Readings();
		         reading12.setLightingReading(90.3);
		         reading12.setPowerReading(90.5);
		         reading12.setWaterReading(82.2);
		         reading12.setGasReading(82.0);
		         reading12.setLightingReadingBeforeChange(0d);
		         reading12.setPowerReadingBeforeChange(0d);
		         reading12.setWaterReadingBeforeChange(0d);
		         reading12.setGasReadingBeforeChange(0d);
		         reading12.setFloor(floor1C);
		         reading12.setReadingDate(readingDate2);
		         readingsRepository.save(reading12);

		         // Reading 13
		         Readings reading13 = new Readings();
		         reading13.setLightingReading(97.3);
		         reading13.setPowerReading(97.5);
		         reading13.setWaterReading(89.2);
		         reading13.setGasReading(89.0);
		         reading13.setLightingReadingBeforeChange(0d);
		         reading13.setPowerReadingBeforeChange(0d);
		         reading13.setWaterReadingBeforeChange(0d);
		         reading13.setGasReadingBeforeChange(0d);
		         reading13.setFloor(floor2C);
		         reading13.setReadingDate(readingDate2);
		         readingsRepository.save(reading13);

		         // Reading 14 (Already provided in a previous response)

		         // Reading 15
		         Readings reading15 = new Readings();
		         reading15.setLightingReading(104.3);
		         reading15.setPowerReading(104.5);
		         reading15.setWaterReading(96.2);
		         reading15.setGasReading(96.0);
		         reading15.setLightingReadingBeforeChange(0d);
		         reading15.setPowerReadingBeforeChange(0d);
		         reading15.setWaterReadingBeforeChange(0d);
		         reading15.setGasReadingBeforeChange(0d);
		         reading15.setFloor(floor3C);
		         reading15.setReadingDate(readingDate2);
		         readingsRepository.save(reading15);

		         // Reading 16
		         Readings reading16 = new Readings();
		         reading16.setLightingReading(111.3);
		         reading16.setPowerReading(111.5);
		         reading16.setWaterReading(103.2);
		         reading16.setGasReading(103.0);
		         reading16.setLightingReadingBeforeChange(0d);
		         reading16.setPowerReadingBeforeChange(0d);
		         reading16.setWaterReadingBeforeChange(0d);
		         reading16.setGasReadingBeforeChange(0d);
		         reading16.setFloor(floor1C);
		         reading16.setReadingDate(readingDate3);
		         readingsRepository.save(reading16);

		         // Reading 17
		         Readings reading17 = new Readings();
		         reading17.setLightingReading(118.3);
		         reading17.setPowerReading(118.5);
		         reading17.setWaterReading(110.2);
		         reading17.setGasReading(110.0);
		         reading17.setLightingReadingBeforeChange(0d);
		         reading17.setPowerReadingBeforeChange(0d);
		         reading17.setWaterReadingBeforeChange(0d);
		         reading17.setGasReadingBeforeChange(0d);
		         reading17.setFloor(floor2C);
		         reading17.setReadingDate(readingDate3);
		         readingsRepository.save(reading17);

		      // Reading 18
		         Readings reading18 = new Readings();
		         reading18.setLightingReading(125.3);
		         reading18.setPowerReading(123.5);
		         reading18.setWaterReading(117.2);
		         reading18.setGasReading(118.0);
		         reading18.setLightingReadingBeforeChange(0d);
		         reading18.setPowerReadingBeforeChange(0d);
		         reading18.setWaterReadingBeforeChange(0d);
		         reading18.setGasReadingBeforeChange(0d);
		         reading18.setFloor(floor3C);
		         reading18.setReadingDate(readingDate3);
		         readingsRepository.save(reading18);
		         
		         User user = new User();
		         user.setUsername("default_user");
		         user.setPassword(encoder.encode("admin"));
		         user.setRole(Role.ADMIN);
		         userRepo.save(user);
		};
	}

}
