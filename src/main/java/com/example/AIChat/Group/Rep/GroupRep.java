package com.example.AIChat.Group.Rep;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface GroupRep extends JpaRepository <Group, Long> {

}
