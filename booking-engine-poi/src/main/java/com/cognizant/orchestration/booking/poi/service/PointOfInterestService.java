package com.cognizant.orchestration.booking.poi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cognizant.orchestration.booking.poi.dto.Location;
import com.cognizant.orchestration.booking.poi.dto.PointOfInterest;
import com.cognizant.orchestration.booking.poi.dto.PointOfInterestRequest;

@Service
public class PointOfInterestService {
    @Resource
    private Map<Location, List<PointOfInterest>> poiMap;

    public List<PointOfInterest> getAllPointOfInterestByLatLong(final PointOfInterestRequest request) {
        final List<PointOfInterest> poiList = new ArrayList<PointOfInterest>();
        final Location location = request.getLocation();
        if (poiMap.containsKey(location)) {
        	poiList.addAll(poiMap.get(location));
        }
        return poiList;
    }
}
