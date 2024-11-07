package com.example.AIChat.Sockets.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {
    private String answer;
    private String messageId;
    private int created;
}
