package org.vm93.cinemille.repo;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.vm93.cinemille.model.Schedule;

public interface ScheduleRepo extends PagingAndSortingRepository<Schedule, UUID>, CrudRepository<Schedule, UUID> {
}
