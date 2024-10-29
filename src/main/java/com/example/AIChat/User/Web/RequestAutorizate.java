package com.example.AIChat.User.Web;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UZ")
public class RequestAutorizate {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @NotNull(message = "Логин или пароль неверные")
    private String login;

    @NotNull(message = "Логин или пароль неверные")
    private String password;

    @NotNull(message = "UserId == 0")
    private String userId;

    }

