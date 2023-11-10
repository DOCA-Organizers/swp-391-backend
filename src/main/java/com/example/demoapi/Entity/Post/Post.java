package com.example.demoapi.Entity.Post;

import com.example.demoapi.Entity.Pet.Pet_Breed;
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

    @Column(name= "content",nullable = false)
    private String  content;

    @Column(name="isactive",nullable = false)
    private boolean isActive;

    @Column(name="createtime",nullable = false)
    private Date createTime;

    @Column(name="issold",nullable = false)
    private boolean isSold;

    @Column(name="isexchange",nullable = false)
    private boolean isExchange;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="categoryid",nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userid", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Bookmark> bookmarks;

    @JsonIgnore
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<React> reacts;

    @JsonIgnore
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Report> reports;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pet_Breedid", nullable = false)
    private Pet_Breed pet_Breed;

}
