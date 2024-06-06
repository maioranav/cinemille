package org.vm93.cinemille.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.vm93.cinemille.model.User;
import org.vm93.cinemille.repo.UserRepo;

import jakarta.persistence.EntityExistsException;

@Service
public class AdminService {

	@Autowired
	UserRepo repo;

	@Autowired
	@Qualifier("adminGenerator")
	private ObjectProvider<User> adminGenerator;

	public User saveAdminProfile() {
		User a = adminGenerator.getObject();
		if (repo.existsByUsername(a.getUsername())) {
			throw new EntityExistsException("Admin user already configured");
		}

		repo.save(a);
		return a;
	}
}