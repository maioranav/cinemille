package org.vm93.cinemille.repo;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.vm93.cinemille.model.Film;

public interface FilmRepo extends PagingAndSortingRepository<Film, UUID>, CrudRepository<Film, UUID> {

    @Query("SELECT f FROM Film f WHERE f.releaseDate <= CURRENT_DATE AND f.endDate >= CURRENT_DATE")
    Page<Film> findAttuali(Pageable pageable);

}
