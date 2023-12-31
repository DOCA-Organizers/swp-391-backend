package com.example.demoapi.Entity.Post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "tblCategory")
@Data
public class Category {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name="name",nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Collection<Post> posts;
}
