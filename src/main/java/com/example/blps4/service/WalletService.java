package com.example.blps4.service;

import com.example.blps4.dto.request.UserDto;
import com.example.blps4.dto.request.WalletDto;
import com.example.blps4.entity.ItemEntity;
import com.example.blps4.entity.OrderEntity;
import com.example.blps4.entity.UsersEntity;
import com.example.blps4.entity.WalletEntity;
import com.example.blps4.enums.OrderState;
import com.example.blps4.enums.Payment;
import com.example.blps4.repositories.ItemRepository;
import com.example.blps4.repositories.OrderRepository;
import com.example.blps4.repositories.UsersRepository;
import com.example.blps4.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletService {
    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional(rollbackFor = {Exception.class}, isolation = Isolation.SERIALIZABLE)
    public void paymentForOder(UserDto userDto, int id) throws Exception {
        String username = userDto.getUsername();
        Optional<UsersEntity> user = usersRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new Exception("User with username " + username + " not found");
        }

        if (!user.get().getPassword().equals(userDto.getPassword())) {
            throw new Exception("Wrong password");
        }

        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);

        if (orderEntityOptional.isEmpty()) {
            throw new Exception("Order with id " + id + " not found");
        }

        OrderEntity order = orderEntityOptional.get();
        if (!user.get().equals(order.getUsersByCostumer())) {
            throw new Exception("Other person order");
        }

        if (order.getStatus() != OrderState.READY.ordinal()) {
            throw new Exception("Order not ready for pay");
        }

        Optional<WalletEntity> consumerWalletOptional = walletRepository.findByUsersByUserId(user.get());
        Optional<WalletEntity> traderWalletOptional = walletRepository.findByUsersByUserId(order.getUsersBySeller());

        if (consumerWalletOptional.isEmpty()) {
            throw new Exception("Consumer wallet not found");
        }

        if (traderWalletOptional.isEmpty()) {
            throw new Exception("Trader wallet not found");
        }

        WalletEntity consumerWallet = consumerWalletOptional.get();
        WalletEntity traderWallet = traderWalletOptional.get();

        if (order.getPaymentType() == Payment.MONEY.ordinal()) {
            consumerWallet.setAmount(consumerWallet.getAmount() - order.getSum());
            traderWallet.setAmount(traderWallet.getAmount() + order.getSum());

            walletRepository.save(consumerWallet);
            walletRepository.save(traderWallet);
        } else {
            order.getOrderItemsById().forEach(val -> {
                ItemEntity item = val.getItemByItem();

                item.setWalletByWalletId(traderWallet);
                itemRepository.save(item);
            });
        }

        order.setStatus(OrderState.CLOSE.ordinal());
        orderRepository.save(order);
    }

    @Transactional(rollbackFor = {Exception.class}, readOnly = true)
    public WalletEntity getWalletInformation(int userId) throws Exception {
        Optional<UsersEntity> user = usersRepository.findById(userId);

        if (user.isEmpty()) {
            throw new Exception("User with id " + userId + " not found");
        }
        Optional<WalletEntity> wallet = walletRepository.findByUsersByUserId(user.get());

        if (wallet.isEmpty()) {
            throw new Exception("Wallet not found");
        }

        return wallet.get();
    }

    @Transactional(rollbackFor = {Exception.class})
    public void createWallet(WalletDto walletDto) throws Exception {
        Optional<UsersEntity> user = usersRepository.findById(walletDto.getUserId());

        if (user.isEmpty()) {
            throw new Exception("User with id " + walletDto.getUserId() + " not found");
        }

        Optional<WalletEntity> walletEntityOptional = walletRepository.findByUsersByUserId(user.get());

        WalletEntity walletEntity = walletEntityOptional.orElseGet(() -> new WalletEntity(user.get()));

        walletEntity.setAmount(walletDto.getSum() + walletEntity.getAmount());

        walletRepository.save(walletEntity);

        walletDto.getItems().forEach(val -> itemRepository.save(new ItemEntity(val.getName(), val.getPrice(), val.getDescription(), walletEntity)));
    }
}
