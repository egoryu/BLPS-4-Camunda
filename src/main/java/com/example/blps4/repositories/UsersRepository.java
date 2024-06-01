package com.example.blps4.repositories;

import com.example.blps4.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<UsersEntity> findById(int id);

    Page<UsersEntity> findAllByRole(String role, Pageable pageable);

    void save(UsersEntity user);
}
