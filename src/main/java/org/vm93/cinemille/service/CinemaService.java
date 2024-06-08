package org.vm93.cinemille.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vm93.cinemille.model.Cinema;
import org.vm93.cinemille.model.Film;
import org.vm93.cinemille.repo.CinemaRepo;
import org.vm93.cinemille.repo.ScheduleRepo;

import jakarta.persistence.EntityExistsException;
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
	
	public Cinema addCinema(Cinema cinema) {
		if (!cinemaRepo.findByCinemaNo(cinema.getCinemaNo()).isPresent())
			throw new EntityExistsException("CinemaNo already exists!");
		cinemaRepo.save(cinema);
		return cinema;
	}

	public Cinema updateCinema(UUID id, Cinema cinema) {
		Optional<Cinema> existingCinema = cinemaRepo.findById(id);
		if (!existingCinema.isPresent())
			throw new EntityNotFoundException("Cinema id not found!");
		cinemaRepo.save(cinema);
		return cinema;
	}

	public Cinema deleteCinema(UUID id) {
		Optional<Cinema> cinema = cinemaRepo.findById(id);
		if (!cinema.isPresent())
			throw new EntityNotFoundException("Cinema id not found!");
		cinemaRepo.delete(cinema.get());
		return cinema.get();
	}
	
	public Page<Cinema> findAll(Pageable pageable){
		return cinemaRepo.findAll(pageable);
	}
	
	public Cinema findById(UUID id){
		Optional<Cinema> cinema = cinemaRepo.findById(id);
		if (!cinema.isPresent())
			throw new EntityNotFoundException("Cinema id not found!");
		return cinema.get();
	}

}
