package com.example.blps4.service;

import com.example.blps4.dto.request.OrderDto;
import com.example.blps4.dto.request.WalletDto;
import com.example.blps4.dto.response.HumanResponseDto;
import com.example.blps4.dto.response.UserResponseDto;
import com.example.blps4.entity.*;
import com.example.blps4.enums.OrderState;
import com.example.blps4.enums.Role;
import com.example.blps4.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TradeService {
    private final HumanRepository humanRepository;
    private final ContractRepository contractRepository;
    private final UsersRepository usersRepository;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    @Transactional(rollbackFor = {Exception.class})
    public List<UserResponseDto> getAllUsersByRole(Pageable pageable, Role role) {
        Page<UsersEntity> traderPage = usersRepository.findAllByRole(role.name(), pageable);
        List<UserResponseDto> response = traderPage.getContent().stream()
                .map(val -> new UserResponseDto(val.getId(), val.getUsername(), val.getEmail(), val.getRole()))
                .toList();

        return response;
    }

    @Transactional(rollbackFor = {Exception.class})
    public List<HumanResponseDto> getUserContractors(Pageable pageable, int id, Role role) throws Exception {
        Optional<UsersEntity> trader = usersRepository.findById(id);

        if (trader.isEmpty()) {
            throw new Exception("User with id " + id + " not found");
        }

        if (trader.get().getRole().equals(role.name())) {
            throw new Exception("User with id " + id + "is not " + role.name());
        }

        List<ContractEntity> contractor = (List<ContractEntity>) trader.get().getContractsById();
        List<UsersEntity> users = contractor.stream()
                .map(ContractEntity::getUsersByContractor)
                .sorted()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
        List<HumanEntity> humans = users.stream().map(humanRepository::findByUserId).filter(Objects::nonNull).toList();
        List<HumanResponseDto> response = humans.stream().map(HumanResponseDto::new).toList();

        return response;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void createOrder(OrderDto orderDto) throws Exception {
        Optional<UsersEntity> user = usersRepository.findById(orderDto.getUserId());

        if (user.isEmpty()) {
            throw new Exception("User with id " + orderDto.getUserId() + " not found");
        }

        Optional<UsersEntity> trader = usersRepository.findById(orderDto.getTraderId());

        if (trader.isEmpty()) {
            throw new Exception("Trader with id " + orderDto.getUserId() + " not found");
        }

        Optional<UsersEntity> contactor = usersRepository.findById(orderDto.getContractorId());

        if (contactor.isEmpty()) {
            throw new Exception("Contractor with id " + orderDto.getUserId() + " not found");
        }

        if (!contractRepository.existsByUsersByContractorAndUsersByMaster(contactor.get(), trader.get())) {
            throw new Exception("Contract between " + trader.get().getId() + " and " + contactor.get().getId() + " not found");
        }

        OrderEntity order1 = new OrderEntity(orderDto.getPaymentType(), orderDto.getSum(),
                LocalDateTime.now(), OrderState.OPEN.ordinal(), user.get(), trader.get(), contactor.get());
        log.info(orderDto.getData().toString());

        orderRepository.save(order1);

        log.info("kek");
    }

    @Transactional(rollbackFor = {Exception.class}, isolation = Isolation.SERIALIZABLE)
    public void updateOrder(int id, int status) throws Exception {
        Optional<OrderEntity> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            throw new Exception("Order with id " + id + " not found");
        }

        OrderEntity orderEntity = order.get();
        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);
    }

    @Transactional(rollbackFor = {Exception.class}, isolation = Isolation.SERIALIZABLE)
    public void updateOrderItem(WalletDto walletDto, int id) throws Exception {
        Optional<OrderEntity> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            throw new Exception("Order with id " + id + " not found");
        }

        OrderEntity orderEntity = order.get();
        orderItemRepository.deleteAllByOrderId(orderEntity);

        walletDto.getItems().forEach(val -> {
            Optional<ItemEntity> item = itemRepository.findById(val.getId());

            item.ifPresent(itemEntity -> orderItemRepository.save(new OrderItemEntity(orderEntity, itemEntity)));
        });
        orderEntity.setStatus(OrderState.ADDED.ordinal());

        orderRepository.save(orderEntity);
    }
}
