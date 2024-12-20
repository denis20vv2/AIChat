package com.example.AIChat.Group.Converter;

import com.example.AIChat.Group.DTO.GroupDTO;
import com.example.AIChat.Group.DTO.PostGroupReq;
import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Message.Converter.MessageToReactionDTOConverter;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.User.Domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GroupToGroupDTOConverter implements Converter<Group, GroupDTO> {

    private final MessageToReactionDTOConverter messageToReactionDTOConverter;

    @Override
    public GroupDTO convert(Group group) {

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setGroupId(group.getGroupId());
        groupDTO.setAvatar(group.getAvatar());
        groupDTO.setName(group.getName());
        groupDTO.setUsers(group.getUsers());

        Message message = group.getLastMessage();

        if(message != null) {

            groupDTO.setLastMessage(messageToReactionDTOConverter.convert(message));
        }


        return groupDTO;
    }
}
