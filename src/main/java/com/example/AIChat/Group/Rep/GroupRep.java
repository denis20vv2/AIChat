package com.example.AIChat.Group.Rep;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.User.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRep extends JpaRepository <Group, Long> {


    //Group findByUserId(UUID userId);
    Page<Group> findByUsersUserId (String userId, Pageable pageable);

    //Page<Group> findByUserUserId (String userId);

    //Group findByUserUserId (String groupId);

    Group findByGroupId(String groupId);
}
