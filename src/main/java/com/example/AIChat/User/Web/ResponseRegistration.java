package com.example.AIChat.User.Web;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseRegistration {

    @NotNull(message = "Ошибка заполения статуса валидации")
    private boolean status;

    @NotNull(message = "Ошибка заполения message_status")
    private String messageStatus;

}
