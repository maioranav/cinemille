package org.vm93.cinemille.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vm93.cinemille.model.Cinema;
import org.vm93.cinemille.model.Schedule;
import org.vm93.cinemille.repo.ScheduleRepo;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepo scheduleRepo;

	@Autowired
	private CinemaService cinemaService;

	private boolean validateDuration(Schedule schedule) {
		long durata = ChronoUnit.DAYS.between(schedule.getStartDate(), schedule.getEndDate());
		return durata >= 7 && durata <= 21;
	}

	public Schedule addShow(Schedule schedule) {
		if (!validateDuration(schedule)) {
			throw new IllegalArgumentException("Data range must be between 1-3 weeks");
		}

		if (!cinemaService.isAvailable(schedule.getCinema(), schedule.getStartDate(), schedule.getEndDate())) {
			throw new IllegalStateException("Cinema not available in this Date Range!");
		}

		scheduleRepo.save(schedule);
		return schedule;
	}

	public Schedule updateShow(Schedule schedule) {
		Optional<Schedule> existSchedule = scheduleRepo.findById(schedule.getId());

		if (!existSchedule.isPresent())
			throw new IllegalStateException("Schedule not found!");
		scheduleRepo.save(schedule);
		return schedule;

	}

	public Schedule removeShow(UUID id) {
		Optional<Schedule> existSchedule = scheduleRepo.findById(id);

		if (!existSchedule.isPresent())
			throw new IllegalStateException("Schedule not found!");
		scheduleRepo.delete(existSchedule.get());
		return existSchedule.get();
	}

	public Page<Schedule> getHistorycalFilm(Pageable pageable, LocalDate fromDate, LocalDate toDate) {
		Page<Schedule> list = (Page<Schedule>) new ArrayList<Schedule>();
		return list;
	}

	public Page<Schedule> getActiveShows(Pageable pageable) {
		Page<Schedule> list = (Page<Schedule>) new ArrayList<Schedule>();
		return list;
	}

	public Schedule findById(UUID id) {
		Optional<Schedule> existSchedule = scheduleRepo.findById(id);

		if (!existSchedule.isPresent())
			throw new IllegalStateException("Schedule not found!");

		return existSchedule.get();
	}

}
