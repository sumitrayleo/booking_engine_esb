package com.cognizant.orchestration.booking.poi.dto;

import java.util.List;

public class PointOfInterestResponse {
    private List<PointOfInterest> pointsOfInterest;

    public List<PointOfInterest> getPointOfInterests() {
        return pointsOfInterest;
    }

    public void setPointOfInterests(List<PointOfInterest> pointOfInterests) {
        this.pointsOfInterest = pointOfInterests;
    }

}
