package com.example.demoapi.Entity.Pet;

import com.example.demoapi.Entity.Post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "Pet_Item")
@Data
public class Pet_Item {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String id;

    @Column(name = "pet_item")
    private String pet_item;

    @Column(name = "pet_type")
    private String pet_type;

    @Column(name = "img", nullable = false)
    private String img;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private String price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post postId;

}
