package com.example.blps4.repositories;

import com.example.blps4.entity.ContractEntity;
import com.example.blps4.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends CrudRepository<ContractEntity, Integer> {
    Boolean existsByUsersByContractorAndUsersByMaster(UsersEntity contractor, UsersEntity master);

    List<ContractEntity> findAllByUsersByMaster(UsersEntity usersByMaster);
}
