package com.chandan.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandan.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
