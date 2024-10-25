package com.example.AIChat.Group.Converter;

import com.example.AIChat.Group.DTO.PostGroupReq;
import com.example.AIChat.Group.Domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupPostReqToGroupConverter implements Converter<PostGroupReq, Group> {

    //private final  ;

    @Override
    public Group convert(PostGroupReq postGroupReq) {

        Group group = new Group();
        group.setAvatar(postGroupReq.getAvatar());
        group.setName(postGroupReq.getName());
        group.setName(postGroupReq.getName());
        //group.setLastMessage(postGroupReq.getLastMessage());

        //postGroupReq.getUserId();

        return group;
    }
}
