package org.vm93.cinemille.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vm93.cinemille.model.Cinema;
import org.vm93.cinemille.model.Film;
import org.vm93.cinemille.model.Schedule;
import org.vm93.cinemille.repo.ScheduleRepo;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepo scheduleRepo;

	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private FilmService filmService;

	private boolean validateDuration(Schedule schedule) {
		long durata = ChronoUnit.DAYS.between(schedule.getStartDate(), schedule.getEndDate());
		return durata >= 7 && durata <= 21;
	}

	public Schedule addShow(Schedule schedule) {
		if (!validateDuration(schedule)) {
			throw new IllegalArgumentException("Data range must be between 1-3 weeks");
		}

		if (!cinemaService.isAvailable(schedule.getCinema(), schedule.getStartDate(), schedule.getEndDate())) {
			throw new IllegalStateException("Cinema not available in this Date Range!");
		}

		scheduleRepo.save(schedule);
		return schedule;
	}

	public Schedule updateShow(Schedule schedule) {
		Optional<Schedule> existSchedule = scheduleRepo.findById(schedule.getId());

		if (!existSchedule.isPresent())
			throw new IllegalStateException("Schedule not found!");
		scheduleRepo.save(schedule);
		return schedule;

	}

	public Schedule removeShow(UUID id) {
		Optional<Schedule> existSchedule = scheduleRepo.findById(id);

		if (!existSchedule.isPresent())
			throw new IllegalStateException("Schedule not found!");
		scheduleRepo.delete(existSchedule.get());
		return existSchedule.get();
	}

	public Page<Schedule> getHistorycalFilm(Pageable pageable, LocalDate fromDate, LocalDate toDate) {
		Page<Schedule> list = scheduleRepo.findByDateRange(fromDate, toDate, pageable);
		return list;
	}

	public Page<Schedule> getActiveShows(Pageable pageable) {
		Page<Schedule> list = scheduleRepo.findActive(pageable);
		return list;
	}

	public Schedule findById(UUID id) {
		Optional<Schedule> existSchedule = scheduleRepo.findById(id);

		if (!existSchedule.isPresent())
			throw new IllegalStateException("Schedule not found!");

		return existSchedule.get();
	}
	
	public void importXLSX(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                String isbn = row.getCell(0).getStringCellValue();
                String titolo = row.getCell(1).getStringCellValue();
                int salaName = (int) row.getCell(2).getNumericCellValue();
                LocalDate startDate = LocalDate.from(row.getCell(3).getLocalDateTimeCellValue());
                LocalDate endDate = LocalDate.from(row.getCell(4).getLocalDateTimeCellValue());

                Film film = filmService.findOrCreateFilm(isbn, titolo, LocalDate.now());
                Cinema cinema = cinemaService.findByCinemaNo(salaName);

                if (!cinemaService.isAvailable(cinema, startDate, endDate)) {
                    throw new IllegalStateException("Cinema is not available for this Date range.");
                }

                Schedule schedule = Schedule.builder().cinema(cinema).film(film).startDate(startDate).endDate(endDate).build();
                scheduleRepo.save(schedule);
            }
        }
    }

    public ByteArrayInputStream esportaScheduleInExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Schedule");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ISBN");
            header.createCell(1).setCellValue("Title");
            header.createCell(2).setCellValue("Cinema");
            header.createCell(3).setCellValue("Start Date");
            header.createCell(4).setCellValue("End Date");

            List<Schedule> schedules = scheduleRepo.findAll();
            int rowIdx = 1;
            for (Schedule schedule : schedules) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(schedule.getFilm().getISBN());
                row.createCell(1).setCellValue(schedule.getFilm().getTitle());
                row.createCell(2).setCellValue(schedule.getCinema().getCinemaNo());
                Cell startDateCell = row.createCell(3);
                startDateCell.setCellValue(schedule.getStartDate());
                startDateCell.setCellStyle(createDateCellStyle(workbook));
                Cell endDateCell = row.createCell(4);
                endDateCell.setCellValue(schedule.getEndDate());
                endDateCell.setCellStyle(createDateCellStyle(workbook));
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
