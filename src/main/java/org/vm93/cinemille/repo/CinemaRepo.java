package org.vm93.cinemille.repo;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.vm93.cinemille.model.Cinema;

public interface CinemaRepo extends PagingAndSortingRepository<Cinema, UUID>, CrudRepository<Cinema, UUID> {

}
