package com.cognizant.orchestration.booking.reservation.dto;

import java.util.List;

public class ReservationResponse {

    private List<Reservation> reservations;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}
