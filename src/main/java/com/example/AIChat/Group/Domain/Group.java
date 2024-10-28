package com.example.AIChat.Group.Domain;

import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.User.Domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"group\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String groupId;

    @Column(nullable = false)
    @Schema(description = "адрес аватара", example = "group_avatar1.jpg")
    private String avatar;

    @Column(nullable = false)
    @Schema(description = "имя юзера", example = "userNew")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Column(nullable = false)
    private Set<User> users = new HashSet<>();

    @OneToOne(optional = true)
    @JoinColumn(name = "last_message_id", referencedColumnName = "messageId", nullable = true)
    private Message lastMessage;


}
