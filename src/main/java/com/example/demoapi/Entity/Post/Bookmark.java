package com.example.demoapi.Entity.Post;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.Date;

@Entity
@Table(name="tblBookmark")
@Data
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    private User userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postid")
    private Post postId;

    @Column(name = "createtime", nullable = false)
    private Date createTime;

    @Column(name = "isactive", nullable = false)
    private boolean isActive;

}
