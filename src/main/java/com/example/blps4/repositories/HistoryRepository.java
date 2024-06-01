package com.example.blps4.repositories;

import com.example.blps4.entity.HistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends CrudRepository<HistoryEntity, Integer> {
}
