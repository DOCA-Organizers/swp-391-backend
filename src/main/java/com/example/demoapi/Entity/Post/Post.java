package com.example.demoapi.Entity.Post;

import com.example.demoapi.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "tblPost")
@Data
public class Post {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String id;
    @Column(name= "content")
    private String  content;
    @Column(name = "title")
    private String title;
    @Column(name="status")
    private boolean status;
    @Column(name="createTime")
    private Date createtime;
    @Column(name="isactive")
    private boolean isactive;
    @Column(name="exchange")
    private boolean exchange;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_category",nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userid",nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "postid", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Bookmark> bookmarks;

    @JsonIgnore
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<React> reacts;
}
