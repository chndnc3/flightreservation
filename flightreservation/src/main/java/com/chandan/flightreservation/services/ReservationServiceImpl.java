package com.chandan.flightreservation.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chandan.flightreservation.dto.ReservationRequest;
import com.chandan.flightreservation.entities.Flight;
import com.chandan.flightreservation.entities.Passenger;
import com.chandan.flightreservation.entities.Reservation;
import com.chandan.flightreservation.repos.FlightRepository;
import com.chandan.flightreservation.repos.PassengerRepository;
import com.chandan.flightreservation.repos.ReservationRepository;
import com.chandan.flightreservation.util.EmailUtil;
import com.chandan.flightreservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Value("${com.chandan.flightreservation.itinerary.dirpath}")
	private String ITINERARY_DIR = "/Users/chandankumar/Downloads/Documents/Reservations/Reservation";

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	PDFGenerator pdfGenerator;

	@Autowired
	EmailUtil emailUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Override
	@Transactional
	public Reservation bookFlight(ReservationRequest request) {

		LOGGER.info("Inside bookFlight()");
		// Make Payment

		Long flightId = request.getFlightId();
		LOGGER.info("Fetching flight for flight id:" + flightId);
		Optional<Flight> flight = flightRepository.findById(flightId);

		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());
		LOGGER.info("Saving the passenger:" + passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);

		Reservation reservation = new Reservation();
		reservation.setFlight(flight.get());
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);

		LOGGER.info("Saving the reservation:" + reservation);
		Reservation savedReservation = reservationRepository.save(reservation);

		String filePath = ITINERARY_DIR + savedReservation.getId() + ".pdf";
		LOGGER.info("Generating the itinerary");
		pdfGenerator.generateItinerary(savedReservation, filePath);
		LOGGER.info("Emailing the itinerary");
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		return savedReservation;
	}

}
