package org.vm93.cinemille.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vm93.cinemille.model.Cinema;
import org.vm93.cinemille.repo.CinemaRepo;
import org.vm93.cinemille.repo.ScheduleRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CinemaService {

	@Autowired
	private CinemaRepo cinemaRepo;
	
	@Autowired
	private ScheduleRepo scheduleRepo;

	public boolean isAvailable(Cinema cinema, LocalDate startDate, LocalDate endDate) {
		return scheduleRepo.isScheduleOverlapping(cinema.getCinemaNo(), startDate, endDate).isEmpty();
	}
	
	public Cinema findByCinemaNo(int cinemaNo) {
		Optional<Cinema> cinema = cinemaRepo.findByCinemaNo(cinemaNo);
		if (!cinema.isPresent()) throw new EntityNotFoundException("Cinema not found.");
		return cinema.get();
	}

}
