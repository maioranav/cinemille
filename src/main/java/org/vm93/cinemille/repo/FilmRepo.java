package org.vm93.cinemille.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.vm93.cinemille.model.Film;

public interface FilmRepo extends PagingAndSortingRepository<Film, UUID>, CrudRepository<Film, UUID> {

	Optional<Film> findByISBN(long ISBN);
	
	Page<Film> findAllByOrderByReleaseDateDesc(Pageable pageable);

}
