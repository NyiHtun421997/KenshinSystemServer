package com.system.kenshinsystem.mapper;

import java.time.LocalDate;

import com.system.kenshinsystem.dto.ReadingDTO;
import com.system.kenshinsystem.model.Readings;

public class ReadingMapper {
	
	public static ReadingDTO mapToReadingDTO(Readings readings) {
		
		Double[] readingsArray = {readings.getLightingReading(),readings.getPowerReading(),readings.getWaterReading(),readings.getGasReading()};
		Double[] readingsBeforeChangeArray = {readings.getLightingReadingBeforeChange(),readings.getPowerReadingBeforeChange(),readings.getWaterReadingBeforeChange(),readings.getGasReadingBeforeChange()};
		String buildingName = readings.getFloor().getBuilding().getName();
		String floorName = readings.getFloor().getName();
		LocalDate readingDate = readings.getReadingDate().getDate();
		
		ReadingDTO readingDTO = new ReadingDTO( readingsArray,readingsBeforeChangeArray,buildingName,floorName,readingDate);
												
		return readingDTO;
		
	}
	
	public static ReadingDTO mapToTenantReadingDTO(Readings readings,Double areaRatio) {
		
		Double[] readingsArray = { readings.getLightingReading()*areaRatio, readings.getPowerReading()*areaRatio, readings.getWaterReading()*areaRatio, readings.getGasReading()*areaRatio };
		Double[] readingsBeforeChangeArray = { readings.getLightingReadingBeforeChange()*areaRatio, readings.getPowerReadingBeforeChange()*areaRatio, readings.getWaterReadingBeforeChange()*areaRatio, readings.getGasReadingBeforeChange()*areaRatio};
		String buildingName = readings.getFloor().getBuilding().getName();
		String floorName = readings.getFloor().getName();
		LocalDate readingDate = readings.getReadingDate().getDate();
		
		ReadingDTO readingDTO = new ReadingDTO( readingsArray,readingsBeforeChangeArray,buildingName,floorName,readingDate);
												
		return readingDTO;
		
	}
	

}
