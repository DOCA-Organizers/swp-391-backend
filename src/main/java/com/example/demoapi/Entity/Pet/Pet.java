package com.example.demoapi.Entity.Pet;

import com.example.demoapi.Entity.Post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tblPet")
@Data
public class Pet {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private boolean gender;

    @Column(name = "age")
    private int age;

    @Column(name = "img", nullable = false)
    private String img;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post postId;
}
