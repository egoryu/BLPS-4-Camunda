package com.example.blps4.repositories;

import com.example.blps4.entity.OrderEntity;
import com.example.blps4.entity.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItemEntity, Integer> {
    void deleteAllByOrderId(OrderEntity order);
}
