package com.example.demoapi.Repository.Pet_Breed;

import com.example.demoapi.DTO.Post.Pet_BreedDTO;
import com.example.demoapi.DTO.User.ReportDTO;
import com.example.demoapi.Entity.Pet.Pet_Breed;
import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface MyPet_BreedRepository extends JpaRepository<Pet_Breed,String> {
    Pet_Breed findPet_BreedByPostId(Post post);
    @Query(value = "SELECT DISTINCT [breedname] FROM [dbo].[Pet_Breed]\n" +
            "WHERE [pet_type] = ?1\n" , nativeQuery = true)
    List<Object[]> getBreedNameByPetType(String pet_type);

    default List<Pet_BreedDTO> generateBreedNameList(String pet_type) {
        List<Object[]> data = getBreedNameByPetType(pet_type);
        List<Pet_BreedDTO> breedNameList = new ArrayList<>();
        for (Object[] row : data) {
            Pet_BreedDTO pet_breedDTO = new Pet_BreedDTO();
            pet_breedDTO.setBreedname((String) row[0]);
            breedNameList.add(pet_breedDTO);
        }
        return breedNameList;
    }
}
