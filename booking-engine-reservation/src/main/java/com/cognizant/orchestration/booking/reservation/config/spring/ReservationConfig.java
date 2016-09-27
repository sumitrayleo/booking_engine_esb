package com.cognizant.orchestration.booking.reservation.config.spring;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cognizant.orchestration.booking.reservation.dto.Reservation;
import com.cognizant.orchestration.booking.reservation.util.ProcessReservationData;

@Configuration
public class ReservationConfig {

    
    @Bean
    public Map<String,Reservation> reservationMap()
    {
        final Map<String, Reservation> populateReservationMap = ProcessReservationData.populateReservationMap();
        return populateReservationMap;
        
    }
}
