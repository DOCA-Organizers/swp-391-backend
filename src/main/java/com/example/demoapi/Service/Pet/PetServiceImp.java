package com.example.demoapi.Service.Pet;

import com.example.demoapi.DTO.Post.Pet_BreedDTO;
import com.example.demoapi.DTO.Post.exchangeItem;
import com.example.demoapi.DTO.Post.exchangePet;
import com.example.demoapi.Entity.Pet.Pet;
import com.example.demoapi.Entity.Pet.Pet_Breed;
import com.example.demoapi.Entity.Pet.Pet_Item;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Comment.CommentRepository;
import com.example.demoapi.Repository.Pet.PetRepository;
import com.example.demoapi.Repository.Pet_Breed.Pet_BreedRepository;
import com.example.demoapi.Repository.Pet_Item.Pet_ItemRepository;
import com.example.demoapi.Repository.Post.PostRepository;
import com.example.demoapi.Repository.React.ReactRepository;
import com.example.demoapi.Repository.Report.ReportRepository;
import com.example.demoapi.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PetServiceImp implements PetService{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReactRepository reactRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private Pet_BreedRepository pet_breedRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private Pet_ItemRepository petItemRepository;
//    @Override
//    public Pet_Breed findPet_BreedByPostId(String postid) {
//        try{
//            Post post = postRepository.findPostById(postid);
//            if (post.isActive()){
//                return pet_breedRepository.findPet_BreedByPostId(post);
//            }
//            else return null;
//        }
//        catch(DataIntegrityViolationException e){
//            e.printStackTrace();
//            return null;
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public List<Pet_BreedDTO> getBreedNameByPetType(String pet_type) {
        try{
            return pet_breedRepository.generateBreedNameList(pet_type);
        }
        catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Pet> findPetByPostId(String postid) {
        try{
            Post post = postRepository.findPostById(postid);
            if (post.isActive()){
                return petRepository.findPetByPostId(post);
            }
            else return null;
        }
        catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Pet_Item> findPet_ItemByPostId(String postid) {
        try{
            Post post = postRepository.findPostById(postid);
            if (post.isActive()){
                return petItemRepository.findPet_ItemByPostId(post);
            }
            else return null;
        }
        catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getPetBreedbytypeandname(String type, String name) {
        try{
            Pet_Breed pet_breed = pet_breedRepository.findPetBreedByPetTypeAndBreedname(type,name);
            if (pet_breed!=null){
                return pet_breed.getId();
            }
            else return null;
        }
        catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String addPetBreed(String petType, String petBreed) {
        try{
            Pet_Breed pet_breed = new Pet_Breed();
            pet_breed.setPet_type(petType);
            pet_breed.setBreedname(petBreed);
            pet_breed.setId(UUID.randomUUID().toString());
            pet_breedRepository.save(pet_breed);
            return pet_breed.getId();
        }
        catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int savePets(String postid, List<exchangePet> list) {
        try{
            int result = 0;
            Post post = postRepository.findPostById(postid);
            for ( exchangePet e: list
            ) {
                Pet pet = new Pet();
                pet.setId(UUID.randomUUID().toString());
                pet.setName(e.getName());
                pet.setPrice(e.getPrice());
                pet.setAge(e.getAge());
                pet.setGender(e.isGender());
                pet.setImg(e.getImg());
                pet.setType(e.getType());
                pet.setDescription(e.getDescription());
                pet.setPostId(post);
                petRepository.save(pet);
                result=result+1;
            }
            return result;
        }
        catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return 0;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int saveItems(String postid, List<exchangeItem> list) {
        try {
            int result = 0;
            Post post = postRepository.findPostById(postid);
            for (exchangeItem e : list
            ) {
                Pet_Item pet = new Pet_Item();
                pet.setId(UUID.randomUUID().toString());
                pet.setPet_item(e.getPet_item());
                pet.setPrice(e.getPrice());
                pet.setPet_type(e.getPet_type());
                pet.setImg(e.getImg());
                pet.setDescription(e.getDescription());
                pet.setPostId(post);
                petItemRepository.save(pet);
                result = result + 1;
            }
            return result;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean dupplicateBreed(String type, String name) {
        try{
            Pet_Breed pet_breed = pet_breedRepository.findPetBreedByPetTypeAndBreedname(type,name);
            if (pet_breed!=null){
                return true;
            }
            else return false;
        }
        catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
