package com.cognizant.orchestration.booking.poi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.orchestration.booking.poi.dto.PointOfInterest;
import com.cognizant.orchestration.booking.poi.dto.PointOfInterestRequest;
import com.cognizant.orchestration.booking.poi.dto.PointOfInterestResponse;
import com.cognizant.orchestration.booking.poi.service.PointOfInterestService;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
public class PointOfInterestController {
    @Autowired
    private PointOfInterestService pointOfInterestService;

    @RequestMapping(value="/api/info/getAllPointsOfInterest", method = RequestMethod.POST)
    public PointOfInterestResponse getAllPointsOfInterest(
        @ApiParam(value = "Get All Points Of Interest Request") @RequestBody final PointOfInterestRequest pointOfInterestRequest) {
        final List<PointOfInterest> pointsOfInterest =
            pointOfInterestService.getAllPointOfInterestByLatLong(pointOfInterestRequest);
        final PointOfInterestResponse resp = new PointOfInterestResponse();
        resp.setPointOfInterests(pointsOfInterest);
        return resp;
    }

}
