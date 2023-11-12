package com.example.demoapi.Entity.Post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tblPostimg")
@Data
public class PostImg {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String id;
    @Column(name = "img", nullable = false, updatable = false, unique = true)
    private String img;
    @Column(name = "description", updatable = false, unique = true)
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postid")
    private Post postId;
}
