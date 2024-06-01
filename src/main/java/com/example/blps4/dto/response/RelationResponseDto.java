package com.example.blps4.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RelationResponseDto {
    private int userId;
    private String relation;
}
