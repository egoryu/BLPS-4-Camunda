package com.example.blps4.repositories;

import com.example.blps4.entity.RelationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepository extends CrudRepository<RelationEntity, Integer> {
}
