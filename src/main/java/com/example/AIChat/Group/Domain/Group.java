package com.example.AIChat.Group.Domain;

import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.User.Domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    @Column(nullable = false)
    private String GroupId;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Column(nullable = false)
    private Set<User> users = new HashSet<>();

    @OneToOne(optional = false)
    @JoinColumn(name = "message_id", referencedColumnName = "messageId", nullable = false)
    private Message lastMessage;
















    ////////////////////////////////////////////////

    /*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Column(nullable = false)
    private Set<Book> books = new HashSet<>();

    @Id
    @GenericGenerator(
            name = "author_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "author_seq"),
                    @org.hibernate.annotations.Parameter(name= "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name= "initial_value", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String authorName;*/


}
