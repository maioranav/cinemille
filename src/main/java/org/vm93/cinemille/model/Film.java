package org.vm93.cinemille.model;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "films")
@Data @Builder
public class Film {
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
    
    private String ISBN;
	
	private String title;
	
	private Date releaseDate;
	
	private Date endDate;
	
	@ManyToOne
	private Cinema cinema;
	
	public int getDuration() {
		return 1;
	}
	
	public Boolean dateValidation() {
		return true;
	}

}
