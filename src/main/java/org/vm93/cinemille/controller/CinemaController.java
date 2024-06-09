package org.vm93.cinemille.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vm93.cinemille.model.Cinema;
import org.vm93.cinemille.service.CinemaService;

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

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getCinemaByID(@PathVariable(name = "id") UUID id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> addNewCinema(@RequestBody Cinema cinema){
		return new ResponseEntity<>(service.addCinema(cinema), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> deleteCinemaByID(@PathVariable(name = "id") UUID id) {
		return new ResponseEntity<>(service.deleteCinema(id), HttpStatus.OK);
	}

}
