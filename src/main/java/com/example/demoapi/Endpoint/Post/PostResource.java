package com.example.demoapi.Endpoint.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.User;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Service.Post.PostService;
import com.example.demoapi.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/search/category={categoryid}")
    public ResponseEntity<?> getPostbyCategory(@PathVariable("categoryid") int categoryid) {
        List<Post> list = postService.findPostsByCategory(categoryid);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @GetMapping("/profile/post/userid={userid}")
    public ResponseEntity<?> getPostsbyUser(@PathVariable("userid") String userid){
        List<Post> list = postService.findPostsByUser(userid);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/react/post/{userid}/{id}")
    public ResponseEntity<?> reactAPost(@PathVariable("userid") String userid, @PathVariable("id") String id){
        if(postService.reactAPostOrComment(userid, id, id)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("React failed");
    }

    @GetMapping("/react/count/{id}")
    public ResponseEntity<?> countReact(@PathVariable("id") String id){
        if(postService.countReact(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.countReact(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Count react failed");
    }

}


