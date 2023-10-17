package com.example.demoapi.Entity.Post;

import com.example.demoapi.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "tblComment")
@Data
public class Comment {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "createtime", nullable = false)
    private Date createTime;

    @Column(name = "isactive", nullable = false)
    private boolean isActive;

    @Column(name="isprivate",nullable = false)
    private boolean isPrivate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post postId;

    @JsonIgnore
    @OneToMany(mappedBy = "commentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<React> reacts;
}
