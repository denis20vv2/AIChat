package com.example.AIChat.Group.DTO;

import com.example.AIChat.Message.DTO.MessageReaction;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.User.Domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class GroupDTO {

    @NotNull(message = "groupId data is missing")
    private String groupId;

    @NotNull(message = "avatar data is missing")
    private String avatar;

    @NotNull(message = "name data is missing")
    private String name;

    @NotNull(message = "users data is missing")
    private Set<User> users = new HashSet<>();

    private MessageReaction lastMessage;

}
