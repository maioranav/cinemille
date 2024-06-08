package org.vm93.cinemille.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scheduler")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Film film;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cinema cinema;

	public long getDuration() {
		 return ChronoUnit.DAYS.between(startDate, endDate);
	}

}
