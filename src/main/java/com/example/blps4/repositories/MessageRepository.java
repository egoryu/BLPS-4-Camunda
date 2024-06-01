package com.example.blps4.repositories;

import com.example.blps4.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByType(int type);
}
