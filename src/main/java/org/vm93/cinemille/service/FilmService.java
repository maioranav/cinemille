package org.vm93.cinemille.service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vm93.cinemille.model.Film;
import org.vm93.cinemille.repo.FilmRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FilmService {

	@Autowired
	private FilmRepo repo;

	public Film addFilm(Film film) {
		repo.save(film);
		return film;
	}

	public Film updateFilm(UUID id, Film film) {
		Optional<Film> existingFilm = repo.findById(id);
		if (!existingFilm.isPresent())
			throw new EntityNotFoundException("Film id not found!");
		repo.save(film);
		return film;
	}

	public Film deleteFilm(UUID id) {
		Optional<Film> film = repo.findById(id);
		if (!film.isPresent())
			throw new EntityNotFoundException("Film id not found!");
		repo.delete(film.get());
		return film.get();
	}

	public Film findByISBN(String isbn) {
		Optional<Film> film = repo.findByISBN(isbn);
		if (!film.isPresent())
			throw new EntityNotFoundException("Film ISBN not found!");
		return film.get();
	}

	public Page<Film> getHistorycalFilm(Pageable pageable, LocalDate fromDate, LocalDate toDate) {
		Page<Film> list = (Page<Film>) new ArrayList<Film>();
		return list;
	}

	public void importXLSX(File file) {
	}

	public File exportXLSX() {
		return new File("Method not implemented yet!");
	}
}
