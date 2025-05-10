package com.example.AIChat.Reaction.Domain;

import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.User.Domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade={CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "reaction_user",
            joinColumns = @JoinColumn(name = "reaction_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> user = new HashSet<>();

    @Column(nullable = false)
    private String emoji;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id", referencedColumnName = "messageId", nullable = false)
    private Message message;
}
