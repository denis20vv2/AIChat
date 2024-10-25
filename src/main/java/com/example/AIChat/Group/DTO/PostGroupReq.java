package com.example.AIChat.Group.DTO;

import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.User.Domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PostGroupReq {

    @Schema(description = "аватар пользователя", example = "avatar1.jpg")
    @NotNull(message = "avatar data is missing")
    private String avatar;
    @Schema(description = "Имя юзера", example = "user")
    @NotNull(message = "nameGroup data is missing")
    private String name;
    @Schema(description = "id юзера")
    @NotNull(message = "userId data is missing")
    private List<String> userIds = new ArrayList<String>();
    @Schema(description = "Последнее сообщение группы", example = "msg1")
    private String lastMessageId;

}
