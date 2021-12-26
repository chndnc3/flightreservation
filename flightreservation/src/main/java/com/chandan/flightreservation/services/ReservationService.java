package com.chandan.flightreservation.services;

import com.chandan.flightreservation.dto.ReservationRequest;
import com.chandan.flightreservation.entities.Reservation;

public interface ReservationService {
	
	public Reservation bookFlight(ReservationRequest request);

}
