package com.example.AIChat.User.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String avatar;

}
