package com.chandan.flightreservation.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chandan.flightreservation.dto.ReservationUpdateRequest;
import com.chandan.flightreservation.entities.Reservation;
import com.chandan.flightreservation.repos.ReservationRepository;

@RestController
@CrossOrigin
public class ReservationRestController {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRestController.class);

	@RequestMapping("/reservations/{id}")
	public Reservation findReservation( @PathVariable("id") Long id) {
		LOGGER.info("Inside findReservation() for id: "+id);
		Optional<Reservation> reservation = reservationRepository.findById(id);
		return reservation.get();
	}
	
	@RequestMapping("/reservations")
	public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
		LOGGER.info("Inside updateReservation() for " + request);
		Optional<Reservation> reservationById = reservationRepository.findById(request.getId());
		Reservation reservation = reservationById.get();
		reservation.setNumberOfBags(request.getNumberOfBags());
		reservation.setCheckedIn(request.getCheckedIn());
		LOGGER.info("Saving Reservation");
		Reservation updatedReservation = reservationRepository.save(reservation);
		return updatedReservation;
	}
	
}
