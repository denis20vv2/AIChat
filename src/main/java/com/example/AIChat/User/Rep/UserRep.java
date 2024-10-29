package com.example.AIChat.User.Rep;

import com.example.AIChat.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRep extends JpaRepository<User, Long> {

  User findByUserId(String userId);

}
