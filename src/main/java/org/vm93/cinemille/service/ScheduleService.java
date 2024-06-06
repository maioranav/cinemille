package org.vm93.cinemille.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vm93.cinemille.model.Schedule;
import org.vm93.cinemille.repo.ScheduleRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleService {

	@Autowired
	ScheduleRepo scheduleRepo;

	public List<Schedule> getActiveSchedule() {
		return (List<Schedule>) scheduleRepo.findAll();
	}

}
