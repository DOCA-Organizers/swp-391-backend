package com.example.demoapi.Entity.User;

import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Bookmark;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.Post.React;
import com.example.demoapi.Entity.Post.Report;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "tblUser")
@Data
public class User {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String id;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "isactive")
    private boolean IsActive;

    @Column(name = "isban")
    private boolean isBan;

    @Column(name = "avt")
    private String avt;

    @JsonIgnore
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<User_Role> user_roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Collection<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Collection<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Collection<React> reacts;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Collection<Report> reports;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Bookmark> bookmarks;
}
