package org.vm93.cinemille.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vm93.cinemille.model.Cinema;
import org.vm93.cinemille.model.Schedule;

public interface ScheduleRepo extends JpaRepository<Schedule, UUID> {

	@Query("SELECT s FROM Schedule s WHERE s.cinema.cinemaNo = :cinemaNo AND "
			+ "(s.startDate < :endDate AND s.endDate > :startDate)")
	List<Schedule> isScheduleOverlapping(@Param(value = "cinemaNo") int cinemaNo,@Param(value = "startDate") LocalDate startDate,@Param(value = "endDate") LocalDate endDate);

	@Query("SELECT s FROM Schedule s WHERE s.cinema.cinemaNo = :cinemaNo AND s.startDate < :endDate AND s.endDate > :startDate")
	Page<Schedule> findByDateRangeAndCinema(@Param(value = "cinemaNo") int cinemaNo,@Param(value = "startDate") LocalDate startDate,@Param(value = "endDate") LocalDate endDate, Pageable pageable);

	@Query("SELECT s FROM Schedule s WHERE s.startDate < :endDate AND s.endDate > :startDate")
	Page<Schedule> findByDateRange(@Param(value = "startDate") LocalDate startDate,@Param(value = "endDate") LocalDate endDate, Pageable pageable);

	@Query("SELECT s FROM Schedule s WHERE s.endDate >= CURRENT_DATE")
	Page<Schedule> findActive(Pageable pageable);
	
	Page<Schedule> findByCinema(Cinema cinema, Pageable pageable);

}