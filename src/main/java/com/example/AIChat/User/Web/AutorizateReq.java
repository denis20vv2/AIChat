package com.example.AIChat.User.Web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Данные запроса")
@Getter
@Setter
public class AutorizateReq {

    @NotNull(message = "не заполнено поле логин")
    private String login;

    @NotNull(message = "Не заполнено поле пароль")
    private String password;


}
