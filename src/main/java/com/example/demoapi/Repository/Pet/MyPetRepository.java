package com.example.demoapi.Repository.Pet;

import com.example.demoapi.Entity.Pet.Pet;
import com.example.demoapi.Entity.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPetRepository extends JpaRepository<Pet, String> {
    List<Pet> findPetByPostId(Post post);
}
