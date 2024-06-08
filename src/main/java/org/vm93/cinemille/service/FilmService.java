package org.vm93.cinemille.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.vm93.cinemille.model.Film;
import org.vm93.cinemille.repo.FilmRepo;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FilmService {

	@Autowired
	private FilmRepo repo;

	public Film addFilm(Film film) {
		if (!repo.findByISBN(film.getISBN()).isPresent())
			throw new EntityExistsException("Film ISBN already exists!");
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
	
	public Page<Film> findAll(Pageable pageable){
		return repo.findAll(pageable);
	}

	public Film findOrCreateFilm(String isbn, String title, LocalDate releaseDate) {
		Optional<Film> optionalFilm = repo.findByISBN(isbn);
		Film film;
		if (optionalFilm.isPresent()) {
			film = optionalFilm.get();
			film.setTitle(title);
			film.setReleaseDate(releaseDate);
		} else {
			film = Film.builder().ISBN(isbn).title(title).releaseDate(releaseDate).build();
		}
		return repo.save(film);
	}

	public void importXLSX(MultipartFile file) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				if (row.getRowNum() == 0)
					continue; // Skip header row
				String isbn = row.getCell(0).getStringCellValue();
				String title = row.getCell(1).getStringCellValue();
				LocalDate releaseDate = LocalDate.from(row.getCell(2).getLocalDateTimeCellValue());
				findOrCreateFilm(isbn, title, releaseDate);
			}
		}
	}

	public ByteArrayInputStream exportXLSX() throws IOException {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Film");
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("ISBN");
			header.createCell(1).setCellValue("Title");
			header.createCell(2).setCellValue("ReleaseDate");

			List<Film> films = (List<Film>) repo.findAll();
			int rowIdx = 1;
			for (Film film : films) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(film.getISBN());
				row.createCell(1).setCellValue(film.getTitle());
				Cell dateCell = row.createCell(2);
				dateCell.setCellValue(film.getReleaseDate());
				dateCell.setCellStyle(createDateCellStyle(workbook));
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	private CellStyle createDateCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
		return cellStyle;
	}
}
