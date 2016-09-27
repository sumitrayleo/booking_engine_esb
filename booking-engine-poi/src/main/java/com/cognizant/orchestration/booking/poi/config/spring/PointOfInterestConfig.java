package com.cognizant.orchestration.booking.poi.config.spring;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cognizant.orchestration.booking.poi.dto.Location;
import com.cognizant.orchestration.booking.poi.dto.PointOfInterest;
import com.cognizant.orchestration.booking.poi.util.ProcessPoiData;

@Configuration
public class PointOfInterestConfig {

    
    @Bean
    public Map<Location, List<PointOfInterest>> poiMap()
    {
        final Map<Location, List<PointOfInterest>> poiMap = ProcessPoiData.populatePointOfInterestMap();
        return poiMap;
        
    }
}
