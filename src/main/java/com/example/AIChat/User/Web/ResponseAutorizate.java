package com.example.AIChat.User.Web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Данные ответа")
@Getter
@Setter
public class ResponseAutorizate {

    @NotNull(message = "Ошибка заполения userId")
    private String userId;

    @NotNull(message = "Ошибка заполения статуса валидации")
    private boolean status;

    @NotNull(message = "Ошибка заполения message_status")
    private String messageStatus;
}
