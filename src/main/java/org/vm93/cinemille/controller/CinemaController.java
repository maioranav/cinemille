package org.vm93.cinemille.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vm93.cinemille.service.CinemaService;

import jakarta.websocket.server.PathParam;

@CrossOrigin(origins = "*", maxAge = 360000)
@RestController
@RequestMapping("/api/v1/cinema")
public class CinemaController {

	@Autowired
	CinemaService service;

	@GetMapping("/all")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getAllCinemas(Pageable pageable) {
		return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping("/:id")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getCinemaByID(@PathParam(value = "id") UUID id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}

}
