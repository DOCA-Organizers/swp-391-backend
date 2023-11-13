package com.example.demoapi.Endpoint.Post;

import com.example.demoapi.DTO.Post.*;
import com.example.demoapi.DTO.User.topDTO;
import com.example.demoapi.Entity.Pet.Pet;
import com.example.demoapi.Entity.Pet.Pet_Breed;
import com.example.demoapi.Entity.Pet.Pet_Item;
import com.example.demoapi.Entity.Post.*;
import com.example.demoapi.Entity.User.User;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Comment.CommentRepository;
import com.example.demoapi.Repository.Bookmark.BookmarkRepository;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Post.PostRepository;
import com.example.demoapi.Service.Bookmark.BookmarkService;
import com.example.demoapi.Service.Pet.PetService;
import com.example.demoapi.Service.Post.PostService;
import com.example.demoapi.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.Pipe;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@CrossOrigin
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class PostResource {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private PetService petService;
    @PostMapping("/React/{userid}/{id}")
    public ResponseEntity<?> reactAPostOrComment(@PathVariable("userid") String userid, @PathVariable("id") String id) {
        if (postService.reactAPostOrComment(userid, id, id)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("React failed!");
    }

    @GetMapping("/react/count/{id}")
    public ResponseEntity<?> countReact(@PathVariable("id") String id) {
        if (postService.countReact(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.countReact(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Count react failed!");
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportAPostOrComment(@RequestBody reportDTO reportDTO) {
        if (postService.reportAPostOrComment(reportDTO.getUserId(),reportDTO.getPostId(),reportDTO.getCommentId(),reportDTO.getMessage())) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report failed!");
    }

    @GetMapping("/report/count/{id}")
    public ResponseEntity<?> countReport(@PathVariable("id") String id) {
        if (postService.countReport(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.countReport(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Count react failed!");
    }
    @GetMapping("/manage/report")
    public ResponseEntity<?> countReport() {
        if (postService.getNumberofReport()!=0) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getNumberofReport());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
    }

    @GetMapping("/report/count")
    public ResponseEntity<?> countPostByReport() {
        if (postService.countPostByReport() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.countPostByReport());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Count react failed!");
    }

    @GetMapping("/report/list")
    public ResponseEntity<?> showListPost() {
        if (postService.showListPostWithNumberOfReport() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.showListPostWithNumberOfReport());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show list failed!");
    }
    @GetMapping("/report/postid={postid}")
        public ResponseEntity<?> showListReportPost(@PathVariable("postid") String postid) {
        if (postService.getReportByPostId(postid)!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getReportByPostId(postid));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("null");
    }

    @GetMapping("/comment/post={postid}")
    public ResponseEntity<?> getCommentByPost(@PathVariable("postid") String postid) {
        List<Comment> list = postService.getCommentsByPost(postid);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/Comment")
    public ResponseEntity<?> createComment(@RequestBody commentDTO commentDTO) {
        if (postService.createComment(commentDTO.getUserId(), commentDTO.getPostId(), commentDTO.getContent())) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment failed!");
    }

    @GetMapping("/comment/count/{postid}")
    public ResponseEntity<?> countComment(@PathVariable("postid") String postid) {
        if (postService.countComment(postid) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.countComment(postid));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Count comment failed");
    }

    @PutMapping("/comment/edit/{commentid}")
    public ResponseEntity<?> updateComment(@RequestBody String content, @PathVariable("commentid") String commentid) {
        if (postService.updateComment(content, commentid)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update Comment failed !");
    }

    @DeleteMapping("/comment/delete/{commentid}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentid") String commentid) {
        if (postService.deleteComment(commentid)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete Comment failed !");
    }


    @DeleteMapping("/post/delete/{postid}")
    public ResponseEntity<?> deletePostByPostId(@PathVariable("postid") String postid) {
        if (postService.findCommentById(postid)!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete Comment failed !");
    }
    @GetMapping("/post/detail/postid={postid}")
    public ResponseEntity<?> getPostbyID(@PathVariable("postid") String postid) {
        Post post = postService.getPostbyPostid(postid);
        if ( post != null) {
            return ResponseEntity.status(HttpStatus.OK).body(post);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Count comment failed");
    }

    @GetMapping("/search/category={categoryid}")
    public ResponseEntity<?> getPostsbyCategory(@PathVariable("categoryid") int categoryid) {
        List<Post> list = postService.findPostsByCategory(categoryid);
        if (!list.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(list);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The category is empty!!!");
    }
    @GetMapping("/search/contentkey={key}")
    public ResponseEntity<?> getPostsbyContent(@PathVariable("key") String key) {
        List<Post> list = postService.searchPostsByContent(key);
        if (!list.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(list);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't not find Posts by this key");
    }

    @GetMapping("/profile/postuserid={userid}")
    public ResponseEntity<?> getPostsbyUser(@PathVariable("userid") String userid) {
        List<Post> list = postService.findPostsByUser(userid);
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
    @GetMapping("/bookmark/userid={userid}&postid={postid}")
    public ResponseEntity<?> showbookmarkpost(@PathVariable("userid") String userid,
            @PathVariable("postid") String postid) {
        Bookmark mark = bookmarkService.findBookmark(userid, postid);
        if (mark != null)
            return ResponseEntity.status(HttpStatus.OK).body(mark);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("false");
    }

    @GetMapping("/bookmark/List/userid={userid}")
    public ResponseEntity<?> showlistbookmarkposts(@PathVariable("userid") String userid) {
        List<Post> list = postService.showBookmarkPosts(userid);
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Do not have any bookmark");
    }

    @PostMapping("/bookmark")
    public ResponseEntity<?> Createbookmark(@RequestBody markDTO mark) {
        if (bookmarkService.markthePost(mark.getUserid(), mark.getPostid()))
            return ResponseEntity.status(HttpStatus.OK).body(true);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bookmark fail!!");
    }
    @PutMapping("/profile/mypost/postid={postid}")
    public ResponseEntity<?> changeExchange(@PathVariable("postid") String postid) {
    boolean result = postService.changeExchange(postid);
        if (result)
            return ResponseEntity.status(HttpStatus.OK).body(result);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't change the exchange!!");
    }
    @PostMapping("/CreatePost")
    public ResponseEntity<?> createPost(@RequestBody createDTO createDTO) {
        String createBreed="";
            if(petService.dupplicateBreed(createDTO.getPet_type(),createDTO.getPet_breed())) {
                createBreed = petService.getPetBreedbytypeandname(createDTO.getPet_type(),createDTO.getPet_breed());
            }
            else
            createBreed = petService.addPetBreed(createDTO.getPet_type(), createDTO.getPet_breed());
        String createPost = postService.createPost(createDTO,createBreed);
        int saveimg = 0;
        if (createDTO.getListpostimg() != null) {
            saveimg = postService.savePostsimg(createPost, createDTO.getListpostimg());
        }
        if (createDTO.getListpet() != null) {
            int savepet = petService.savePets(createPost, createDTO.getListpet());
        }
        if (createDTO.getListitem() != null) {
            int saveItem = petService.saveItems(createPost, createDTO.getListitem());
        }
        if (createDTO != null)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't create Post");
    }
//    @GetMapping("/pet_breed/postid={postid}")
//    public ResponseEntity<?> getPetBreedByPost(@PathVariable("postid") String postid) {
//        Pet_Breed petBreed = petService.findPet_BreedByPostId(postid);
//        if (petBreed != null)
//            return ResponseEntity.status(HttpStatus.OK).body(petBreed);
//        else
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet_breed not found!");
//    }

    @GetMapping("/pet_breed/pet_type={pet_type}")
    public ResponseEntity<?> getBreedNameByPetType(@PathVariable("pet_type") String pet_type) {
        List<Pet_BreedDTO> list = petService.getBreedNameByPetType(pet_type);
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet Type Not Found!");
    }

    @GetMapping("/pet/postid={postid}")
    public ResponseEntity<?> getPetByPost(@PathVariable("postid") String postid) {
        List<Pet> list = petService.findPetByPostId(postid);
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet Not Found!");
    }

    @GetMapping("/pet_item/postid={postid}")
    public ResponseEntity<?> getPetItemByPost(@PathVariable("postid") String postid) {
        List<Pet_Item> list = petService.findPet_ItemByPostId(postid);
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet Item Not Found!");
    }
    @GetMapping("/react/users/postid={postid}")
    public ResponseEntity<?> getUsernameReactbyPostid(@PathVariable("postid") String postid) {
        List<String> list = userService.getUsernameReactbyPostid(postid);
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user react");
    }
    @GetMapping("/react/users/commentid={commentid}")
    public ResponseEntity<?> getUsernameReactbyCommentid(@PathVariable("commentid") String commnetid) {
        List<String> list = userService.getUsernameReactbyCommentid(commnetid);
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user react");
    }
    @GetMapping("/CategoryInfo")
    public ResponseEntity<?> getHome() {
        List<categoryDTO> list = postService.getCategoryDTO();
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("false");
    }
    @GetMapping("/Top3")
    public ResponseEntity<?> topUser() {
        List<topDTO> list = userService.getTopUseridPost();
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("false");
    }
    @GetMapping("/TopList")
    public ResponseEntity<?> topListUser() {
        List<topDTO> list = userService.getListUserinfo();
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("false");
    }
    @GetMapping("/Post/All")
    public ResponseEntity<?> getAll() {
        List<Post> list = postService.getall();
        if (list != null)
            return ResponseEntity.status(HttpStatus.OK).body(list);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("false");
    }

}
