package com.example.AIChat.User.Web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Данные запроса регистрации")
@Getter
@Setter
public class RegistrationReq {

    @NotNull(message = "не заполнено поле логин")
    private String login;

    @NotNull(message = "Не заполнено поле пароль")
    private String password;

    @NotNull(message = "Не заполнено поле имя")
    private String name;

    @NotNull(message = "Не заполнено поле изображение")
    private String avatar;

}
