package org.vm93.cinemille;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.vm93.cinemille.enumerator.CinemaTech;
import org.vm93.cinemille.model.Cinema;
import org.vm93.cinemille.model.Schedule;
import org.vm93.cinemille.repo.ScheduleRepo;
import org.vm93.cinemille.service.CinemaService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CinemaServiceTest {
	
	   @Mock
	    private ScheduleRepo scheduleRepo;

	    @InjectMocks
	    private CinemaService cinemaService;

	    @Test
	    public void testIsAvailable() {
	        Cinema cinema = Cinema.builder().capacity(100).cinemaNo(1).tech(CinemaTech.IMAX).build();
	        LocalDate startDate = LocalDate.now(); // From now
	        LocalDate endDate = LocalDate.from(startDate.plusDays(7)); // Add 7 days

	        when(scheduleRepo.isScheduleOverlapping(anyInt(), any(LocalDate.class), any(LocalDate.class)))
	                .thenReturn(List.of());

	        boolean disponibile = cinemaService.isAvailable(cinema, startDate, endDate);

	        assertTrue(disponibile);
	        verify(scheduleRepo, times(1)).isScheduleOverlapping(eq(cinema.getCinemaNo()), eq(startDate), eq(endDate));
	    }

	    @Test
	    public void testIsNonDisponibile() {
	        Cinema cinema = Cinema.builder().capacity(100).cinemaNo(1).tech(CinemaTech.IMAX).build();
	        LocalDate startDate = LocalDate.now(); // From now
	        LocalDate endDate = LocalDate.from(startDate.plusDays(7));
	        Schedule schedule = Schedule.builder().cinema(cinema).startDate(startDate.minusDays(2)).endDate(startDate.plusDays(2)).build();

	        when(scheduleRepo.isScheduleOverlapping(anyInt(), any(LocalDate.class), any(LocalDate.class)))
	                .thenReturn(List.of(schedule));

	        boolean isAvailable = cinemaService.isAvailable(cinema, startDate, endDate);

	        assertFalse(isAvailable);
	        verify(scheduleRepo, times(1)).isScheduleOverlapping(eq(cinema.getCinemaNo()), eq(startDate), eq(endDate));
	    }
}
