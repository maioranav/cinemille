package org.vm93.cinemille.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.vm93.cinemille.model.Cinema;

public interface CinemaRepo extends PagingAndSortingRepository<Cinema, UUID>, CrudRepository<Cinema, UUID> {
	
	Optional<Cinema> findByCinemaNo(int cinemaNo);
	
	Page<Cinema> findAllByOrderByCinemaNoAsc(Pageable pageable);

}
