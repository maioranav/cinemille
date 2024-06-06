package org.vm93.cinemille.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vm93.cinemille.service.ScheduleService;

@RestController
@RequestMapping("/api/v1/public")
public class PublicFacingController {

	@Autowired
	ScheduleService service;

	@GetMapping("/all")
	public ResponseEntity<?> getSchedule() {
		return new ResponseEntity<>(service.getActiveSchedule(), HttpStatus.OK);
	}

}
