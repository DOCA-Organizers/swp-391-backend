package com.example.demoapi.Repository.Pet_Item;

import com.example.demoapi.Entity.Pet.Pet_Item;
import com.example.demoapi.Entity.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPet_ItemRepository extends JpaRepository<Pet_Item, String> {
    List<Pet_Item> findPet_ItemByPostId(Post post);
}
