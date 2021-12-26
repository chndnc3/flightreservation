package com.chandan.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandan.flightreservation.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
