package com.example.demoapi.Service.Pet;

import com.example.demoapi.DTO.Post.Pet_BreedDTO;
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
    @Override
    public Pet_Breed findPet_BreedByPostId(String postid) {
        try{
            Post post = postRepository.findPostById(postid);
            if (post.isActive()){
                return pet_breedRepository.findPet_BreedByPostId(post);
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
}
