package com.example.demoapi.Service.Pet;


import com.example.demoapi.DTO.Post.Pet_BreedDTO;
import com.example.demoapi.Entity.Pet.Pet;
import com.example.demoapi.Entity.Pet.Pet_Breed;
import com.example.demoapi.Entity.Pet.Pet_Item;
import com.example.demoapi.Entity.Post.Post;

import java.util.List;

public interface PetService {
    Pet_Breed findPet_BreedByPostId(String postid);
    List<Pet_BreedDTO> getBreedNameByPetType(String pet_type);
    List<Pet> findPetByPostId(String postid);
    List<Pet_Item> findPet_ItemByPostId(String postid);
}
