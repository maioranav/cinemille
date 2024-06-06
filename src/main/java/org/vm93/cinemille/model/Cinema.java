package org.vm93.cinemille.model;

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
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "cinemas")
@Data @Builder
public class Cinema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "UUID")
	private UUID id;
    
	
	private int cinemaNo;
	
	private int capacity;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tech")
	private CinemaTech tech;

}
