package org.vm93.cinemille.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vm93.cinemille.model.Cinema;
import org.vm93.cinemille.repo.ScheduleRepo;

@Service
public class CinemaService {

	@Autowired
	private ScheduleRepo scheduleRepo;

	public boolean isAvailable(Cinema cinema, LocalDate startDate, LocalDate endDate) {
		return scheduleRepo.isScheduleOverlapping(cinema.getCinemaNo(), startDate, endDate).isEmpty();
	}

}
