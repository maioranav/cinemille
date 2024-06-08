package org.vm93.cinemille.model;

import java.time.LocalDate;
import java.util.UUID;

import org.vm93.cinemille.enumerator.CinemaTech;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cinemas")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Cinema {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "UUID")
	private UUID id;

	@Column(nullable = false, unique = true)
	private int cinemaNo;

	@Column(nullable = false)
	private int capacity;

	@Enumerated(EnumType.STRING)
	@Column(name = "tech", nullable = false)
	private CinemaTech tech;

}
