package com.example.demoapi.Entity.Pet;

import com.example.demoapi.Entity.Post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "Pet_Breed")
@Data
public class Pet_Breed {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String id;

    @Column(name = "breedname")
    private String breedname;

    @Column(name = "pet_type", nullable = false)
    private String pet_type;

    @JsonIgnore
    @OneToMany(mappedBy = "pet_Breed")
    private Collection<Post> posts;
}
