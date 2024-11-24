package com.example.AIChat.User.Rep;

import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Web.RequestAutorizate;

import com.example.AIChat.User.Web.ResponseAutorizate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorizteRep extends JpaRepository<RequestAutorizate, Long> {

    ResponseAutorizate findByUserId(String userId);
    RequestAutorizate findByLogin (String login);

    RequestAutorizate findByPassword (String password);
}
