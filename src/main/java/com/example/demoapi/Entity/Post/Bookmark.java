package com.example.demoapi.Entity.Post;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="tblBookmark")
@Data
public class Bookmark {
    @Id
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    private User userId;

    @Id
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postid")
    private Post postid;

    @Column
    private Date createtime;

}
