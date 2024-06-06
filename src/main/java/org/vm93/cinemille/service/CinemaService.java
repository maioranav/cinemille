package org.vm93.cinemille.service;

import java.sql.Date;

import org.springframework.stereotype.Service;
import org.vm93.cinemille.model.Cinema;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CinemaService {

	public boolean isAvailable(Cinema cinema, Date startDate, Date endDate) {
		return true;
	}

}
