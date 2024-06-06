package org.vm93.cinemille.service;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vm93.cinemille.model.Film;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FilmService {

	public Film addFilm(Film film) {
		return film;
	}

	public Film updateFilm(Integer filmId, Film film) {
		return film;
	}

	public Film deleteFilm(Integer filmId) {
		return Film.builder().build();
	}
	
	public Film findByISBN(String isbn) {
		return Film.builder().build();
	}

	public Page<Film> getHistorycalFilm(Pageable pageable, Date fromDate, Date toDate) {
		Page<Film> list = (Page<Film>) new ArrayList<Film>();
		return list;
	}

	public Page<Film> getActiveFilm(Pageable pageable) {
		Page<Film> list = (Page<Film>) new ArrayList<Film>();
		return list;
	}

	public void importXLSX(File file) {
	}

	public File exportXLSX() {
		return new File("Method not implemented yet!");
	}
}
