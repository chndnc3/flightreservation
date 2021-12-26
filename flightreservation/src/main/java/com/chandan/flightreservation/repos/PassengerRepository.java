package com.chandan.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandan.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
