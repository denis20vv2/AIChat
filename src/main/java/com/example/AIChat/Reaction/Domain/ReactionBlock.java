package com.example.AIChat.Reaction.Domain;

import com.example.AIChat.User.Domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reaction")
public class ReactionBlock {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String reactionId;

    @ManyToMany
    @JoinTable(
            name = "reaction_user",
            joinColumns = @JoinColumn(name = "reaction_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Column(nullable = false)
    private List<User> user;

    @Column(nullable = false)
    private String emoji;
    @Column(nullable = false)
    private String messageId;
}
