package com.example.demoapi.Entity.Post;

import com.example.demoapi.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "tblReact")
@Data
public class React {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "isactive", nullable = false)
    private boolean isActive;

    @Column(name="createtime",nullable = false)
    private Date createTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postid")
    private Post postId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "commentid")
    private Comment commentId;
}
