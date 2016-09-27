package com.cognizant.orchestration.booking.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.orchestration.booking.reservation.dto.Reservation;
import com.cognizant.orchestration.booking.reservation.dto.ReservationRequest;
import com.cognizant.orchestration.booking.reservation.dto.ReservationResponse;
import com.cognizant.orchestration.booking.reservation.service.ReservationService;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
public class BookingReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(value="/api/info/getCurrentandFutureReservations", method=RequestMethod.POST)
    public ReservationResponse getCurrentandFutureReservations(
        @ApiParam(value = "Get Current and Future Reservation Request") @RequestBody final ReservationRequest reservationRequest) {
        final List<Reservation> currentandFutureReservations =
            reservationService.getCurrentandFutureReservations(reservationRequest);
        final ReservationResponse resp = new ReservationResponse();
        resp.setReservations(currentandFutureReservations);
        return resp;
    }

}
