package com.example.demoapi.Entity.Post;

import com.example.demoapi.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tblReport")
@Data
public class Report {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "message")
    private String message;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "createTime", nullable = false)
    private Date createTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post postId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "commentid")
    private Comment commentId;
}
