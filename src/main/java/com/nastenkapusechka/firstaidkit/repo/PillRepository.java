package com.nastenkapusechka.firstaidkit.repo;

import com.nastenkapusechka.firstaidkit.models.Pill;
import org.springframework.data.repository.CrudRepository;

public interface PillRepository extends CrudRepository<Pill, Long> {
}
