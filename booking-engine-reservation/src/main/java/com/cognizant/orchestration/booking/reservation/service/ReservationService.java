package com.cognizant.orchestration.booking.reservation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cognizant.orchestration.booking.reservation.dto.Reservation;
import com.cognizant.orchestration.booking.reservation.dto.ReservationRequest;
import com.cognizant.orchestration.booking.reservation.util.ProcessReservationData;

@Service
public class ReservationService {
    @Resource
    private Map<String, Reservation> reservationMap;

    public List<Reservation> getCurrentandFutureReservations(final ReservationRequest reservationRequest) {
        final List<Reservation> reservations = new ArrayList<Reservation>();
        final List<String> reservationIds = ProcessReservationData.getReservationIds(reservationRequest.getMemberId(),
            reservationRequest.getLatitude(), reservationRequest.getLongitude());
        final Set<String> reservationKey = reservationMap.keySet();
        for (String reservationId : reservationIds) {
            if (reservationKey.contains(reservationId)) {
                reservations.add(reservationMap.get(reservationId));
            }
        }
        return reservations;

    }

}
