package com.example.blps4.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageResponseDto {
    private int messageId;
    private String deleteTime;
    private String date;
    private String messageText;
    private int type;
    private String usernameFrom;
    private String usernameTo;
}
