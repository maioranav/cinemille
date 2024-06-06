package org.vm93.cinemille.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vm93.cinemille.model.User;
import org.vm93.cinemille.payload.AuthResponse;
import org.vm93.cinemille.payload.LoginDTO;
import org.vm93.cinemille.repo.UserRepo;
import org.vm93.cinemille.service.AdminService;
import org.vm93.cinemille.service.AuthService;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 360000)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	AdminService adminService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDto) {
		String token = authService.login(loginDto);
		AuthResponse jwtAuthResponse = new AuthResponse();
		userRepo.findByUsername(loginDto.getUsername()).get();
		jwtAuthResponse.setUsername(loginDto.getUsername());
		jwtAuthResponse.setAccessToken(token);

		return ResponseEntity.ok(jwtAuthResponse);
	}

	@GetMapping("/refresh")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String headers) {
		String oldToken = headers.replace("Bearer ", "");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepo.findByEmail(auth.getName()).get();

		String newToken = authService.refreshToken(oldToken);
		// Prepare the response
		AuthResponse jwtAuthResponse = new AuthResponse();
		jwtAuthResponse.setUsername(user.getUsername());
		jwtAuthResponse.setAccessToken(newToken);

		return ResponseEntity.ok(jwtAuthResponse);
	}

	@GetMapping("/firstboot")
	public ResponseEntity<?> firstBoot() {
		return new ResponseEntity<>(adminService.saveAdminProfile(), HttpStatus.CREATED);
	}

}