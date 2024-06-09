package org.vm93.cinemille.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.vm93.cinemille.payload.DateRangeDTO;
import org.vm93.cinemille.service.ScheduleService;

import jakarta.websocket.server.PathParam;

@CrossOrigin(origins = "*", maxAge = 360000)
@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {
	
	@Autowired
	ScheduleService service;
	
	@GetMapping("/all")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getAllSchedule(Pageable pageable) {
		return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getScheduleByID(@PathVariable(name = "id") UUID id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}
	
	@PostMapping("/range")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getScheduleByDateRance(Pageable pageable, @RequestBody DateRangeDTO dateRange){
		return new ResponseEntity<>(service.getHistorycalFilm(pageable, dateRange.getDateFrom(), dateRange.getDateTo()), HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getActiveSchedule(Pageable pageable) {
		return new ResponseEntity<>(service.getActiveShows(pageable), HttpStatus.OK);
	}
	
    @PostMapping("/import")
    public ResponseEntity<String> importXLSX(@RequestParam("file") MultipartFile file) {
        try {
            service.importXLSX(file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File import issue found.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportXLSX() {
        try {
            ByteArrayInputStream in = service.exportXLSX();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=schedules.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(new InputStreamResource(in));
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

}
