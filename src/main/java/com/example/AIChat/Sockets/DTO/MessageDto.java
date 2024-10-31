package com.example.AIChat.Sockets.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {

        private String roomId;
        private String sender;
        private String content;
        private String timestamp;

}
