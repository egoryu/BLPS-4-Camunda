package com.example.blps4.repositories;

import com.example.blps4.entity.HumanEntity;
import com.example.blps4.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanRepository extends CrudRepository<HumanEntity, Integer> {
    Boolean existsByUserId(UsersEntity userId);

    HumanEntity findByUserId(UsersEntity usersEntity);
}
