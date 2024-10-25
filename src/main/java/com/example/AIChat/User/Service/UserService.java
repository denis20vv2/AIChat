package com.example.AIChat.User.Service;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Rep.GroupRep;
import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Rep.UserRep;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {


    private final GroupRep groupRep;
    private final UserRep userRep;
    private final MessageRep messageRep;


    public List<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return userRep.findAll(pageable).getContent();
    }

}
