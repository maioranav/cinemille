package org.vm93.cinemille.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "films")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String ISBN;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private LocalDate releaseDate;
	
	private String image;
	
	public Boolean dateValidation() {
		return true;
	}

}
