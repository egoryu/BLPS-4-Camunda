package com.example.blps4.controllers;

import com.example.blps4.dto.request.MessageDto;
import com.example.blps4.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/contract")
@AllArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping("/send_invite")
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> sendInvite(@RequestBody MessageDto messageDto) {
        try {
            contractService.sentMessage(messageDto);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    @PutMapping("/send_invite")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAN', 'TRADER')")
    public ResponseEntity<?> updateInvite(@RequestBody MessageDto messageDto) {
        try {
            contractService.editMessage(messageDto);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    @PostMapping("/accept_contract")
    @PreAuthorize("hasRole('MAN')")
    public ResponseEntity<?> acceptContract(@RequestBody MessageDto messageDto) {
        try {
            contractService.acceptContract(messageDto);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }
}
