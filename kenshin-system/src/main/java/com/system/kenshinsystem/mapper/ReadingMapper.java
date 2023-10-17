package com.system.kenshinsystem.mapper;

import java.time.LocalDate;

import com.system.kenshinsystem.dto.ReadingDTO;
import com.system.kenshinsystem.model.Floor;
import com.system.kenshinsystem.model.ReadingDate;
import com.system.kenshinsystem.model.Readings;

public class ReadingMapper {
	
	public static Readings mapToReadings(ReadingDTO readingDTO,Floor floor,ReadingDate readingDate) {
		
		Readings readings = new Readings();
		Double[] readingsDouble = readingDTO.getReadings();
		Double[] reaingsBeforeChangeDouble = readingDTO.getReadingsBeforeChange();
		
		readings.setLightingReading(readingsDouble[0]);
		readings.setPowerReading(readingsDouble[1]);
		readings.setWaterReading(readingsDouble[2]);
		readings.setGasReading(readingsDouble[3]);
		
		readings.setLightingReadingBeforeChange(reaingsBeforeChangeDouble[0]);
		readings.setPowerReadingBeforeChange(reaingsBeforeChangeDouble[1]);
		readings.setWaterReadingBeforeChange(reaingsBeforeChangeDouble[2]);
		readings.setGasReadingBeforeChange(reaingsBeforeChangeDouble[3]);
		
		readings.setComment(readingDTO.getComment());
		
		readings.setFloor(floor);
		readings.setReadingDate(readingDate);
		
		return readings;
	}
	
	public static ReadingDTO mapToReadingDTO(Readings readings) {
		
		Double[] readingsArray = {readings.getLightingReading(),readings.getPowerReading(),readings.getWaterReading(),readings.getGasReading()};
		Double[] readingsBeforeChangeArray = {readings.getLightingReadingBeforeChange(),readings.getPowerReadingBeforeChange(),readings.getWaterReadingBeforeChange(),readings.getGasReadingBeforeChange()};
		String buildingName = readings.getFloor().getBuilding().getName();
		String floorName = readings.getFloor().getName();
		LocalDate readingDate = readings.getReadingDate().getDate();
		String comment = readings.getComment();
		
		ReadingDTO readingDTO = new ReadingDTO( readingsArray,readingsBeforeChangeArray,buildingName,floorName,readingDate,comment);
												
		return readingDTO;
		
	}
	
	public static ReadingDTO mapToTenantReadingDTO(Readings readings,Double areaRatio) {
		
		Double[] readingsArray = { readings.getLightingReading()*areaRatio, readings.getPowerReading()*areaRatio, readings.getWaterReading()*areaRatio, readings.getGasReading()*areaRatio };
		Double[] readingsBeforeChangeArray = { readings.getLightingReadingBeforeChange()*areaRatio, readings.getPowerReadingBeforeChange()*areaRatio, readings.getWaterReadingBeforeChange()*areaRatio, readings.getGasReadingBeforeChange()*areaRatio};
		String buildingName = readings.getFloor().getBuilding().getName();
		String floorName = readings.getFloor().getName();
		LocalDate readingDate = readings.getReadingDate().getDate();
		String comment = readings.getComment();
		
		ReadingDTO readingDTO = new ReadingDTO( readingsArray,readingsBeforeChangeArray,buildingName,floorName,readingDate,comment);
												
		return readingDTO;
		
	}

	//Mapper method to convert Readings for each floor to Readings for each tenant
public static ReadingDTO floorToTenantReadingDTO(ReadingDTO readingDTO,Double areaRatio) {
	
	//multiplying with area ratio for each tenant reading
	Double[] oldReadings =  readingDTO.getReadings();
	Double[] newReadings = { oldReadings[0]*areaRatio, oldReadings[1]*areaRatio, oldReadings[2]*areaRatio, oldReadings[3]*areaRatio };
	
	
	//multiplying with area ratio for each tenant reading before change
	Double[] oldReadingsBeforeChange =  readingDTO.getReadingsBeforeChange();
	Double[] newReadingsBeforeChange = { oldReadingsBeforeChange[0]*areaRatio, oldReadingsBeforeChange[1]*areaRatio, oldReadingsBeforeChange[2]*areaRatio, oldReadingsBeforeChange[3]*areaRatio };
		String buildingName = readingDTO.getBuildingName();
		String floorName = readingDTO.getFloorName();
		LocalDate readingDate = readingDTO.getReadingDate();
		String comment = readingDTO.getComment();
		
		ReadingDTO newReadingDTO = new ReadingDTO( newReadings,newReadingsBeforeChange,buildingName,floorName,readingDate,comment);
												
		return newReadingDTO;
		
	}
	
}
