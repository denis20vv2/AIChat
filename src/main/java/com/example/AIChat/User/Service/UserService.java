package com.example.AIChat.User.Service;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Rep.GroupRep;
import com.example.AIChat.Group.Service.GroupService;
import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Rep.UserRep;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import java.util.*;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);
    private final GroupRep groupRep;
    private final UserRep userRep;
    private final MessageRep messageRep;


    public List<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return userRep.findAll(pageable).getContent();
    }

    public List<User> getUsersByGroupId(String groupId, int page, int size) {

        if("0".equals(groupId)){

            logger.info("groupId = 0");

            return getAllUsers(page, size);

        }

        else
        {

            logger.info("groupId != 0");

        Group group = groupRep.findByGroupId(groupId);
        if (group == null) {
            throw new EntityNotFoundException("Group not found");
        }

        List<User> users = new ArrayList<>(group.getUsers());
        if (users.isEmpty()) {
            throw new IllegalArgumentException("Users is Emty");
        }


        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        int start = Math.min((int) pageable.getOffset(), users.size());
        int end = Math.min((start + pageable.getPageSize()), users.size());


        return users.subList(start, end);}
    }


}
