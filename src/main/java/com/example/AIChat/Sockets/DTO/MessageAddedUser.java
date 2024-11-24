package com.example.AIChat.Sockets.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageAddedUser {

    private String message = "Добавлены новые пользователи";

    private int count;

}
