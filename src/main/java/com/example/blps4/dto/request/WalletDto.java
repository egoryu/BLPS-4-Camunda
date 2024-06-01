package com.example.blps4.dto.request;

import com.example.blps4.entity.WalletEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WalletDto {
    private int userId;
    private int sum;
    private List<ItemDto> items;

    public WalletDto(WalletEntity wallet) {
        this.userId = wallet.getId();
        this.sum = wallet.getAmount();
        this.items = wallet.getItemsById().stream().map(ItemDto::new).toList();
    }
}
