package com.example.blps4.repositories;

import com.example.blps4.entity.UsersEntity;
import com.example.blps4.entity.WalletEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<WalletEntity, Integer> {
    Optional<WalletEntity> findByUsersByUserId(UsersEntity usersEntity);
}
