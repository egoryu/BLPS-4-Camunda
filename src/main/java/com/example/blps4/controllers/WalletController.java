package com.example.blps4.controllers;

import com.example.blps4.dto.request.UserDto;
import com.example.blps4.dto.request.WalletDto;
import com.example.blps4.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class WalletController {
    private final WalletService walletService;


    @PostMapping("/deposit")
    public ResponseEntity<?> makeDeposit(@RequestBody WalletDto walletDto) {
        try {
            walletService.createWallet(walletDto);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<?> payForOrder(@RequestBody UserDto userDto, @PathVariable int id) {
        try {
            walletService.paymentForOder(userDto, id);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/wallet/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable int userId) {
        try {
            return ResponseEntity.ok(new WalletDto(walletService.getWalletInformation(userId)));
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
