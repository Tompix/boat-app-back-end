package com.owt.boat.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.owt.boat.app.model.Boat;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Long>{}
