package com.example.blps4.controllers;

import com.example.blps4.dto.request.HumanDto;
import com.example.blps4.dto.response.HumanResponseDto;
import com.example.blps4.service.HumanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@AllArgsConstructor
public class HumanController {
    private final HumanService humanService;

    @PostMapping("/")
    public ResponseEntity<?> addInformation(@RequestBody HumanDto humanDto) {
        try {
            humanService.addHumanInformation(humanDto);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHuman(@PathVariable int id) {
        try {
            return ResponseEntity.ok(new HumanResponseDto(humanService.getHumanInformation(id)));
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteHuman(@PathVariable int id) {
        try {
            humanService.deleteHumanInformation(id);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
