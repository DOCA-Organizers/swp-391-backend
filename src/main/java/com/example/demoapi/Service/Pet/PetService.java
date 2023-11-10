package com.example.demoapi.Service.Pet;


import com.example.demoapi.DTO.Post.Pet_BreedDTO;
import com.example.demoapi.DTO.Post.exchangeItem;
import com.example.demoapi.DTO.Post.exchangePet;
import com.example.demoapi.Entity.Pet.Pet;
import com.example.demoapi.Entity.Pet.Pet_Breed;
import com.example.demoapi.Entity.Pet.Pet_Item;
import com.example.demoapi.Entity.Post.Post;

import java.util.List;

public interface PetService {
   // Pet_Breed findPet_BreedByPostId(String postid);
    List<Pet_BreedDTO> getBreedNameByPetType(String pet_type);
    List<Pet> findPetByPostId(String postid);
    List<Pet_Item> findPet_ItemByPostId(String postid);
    String getPetBreedbytypeandname(String type,String name);
    String addPetBreed(String petType, String petBreed);
    int savePets(String postid, List<exchangePet> list);
    int saveItems(String postid,List<exchangeItem> list);
    boolean dupplicateBreed(String type,String name);
}
