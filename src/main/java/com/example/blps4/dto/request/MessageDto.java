package com.example.blps4.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDto {
    private int messageId;
    private LocalDateTime date;
    private String messageText;
    private int type;
    private String usernameFrom;
    private String usernameTo;
}
