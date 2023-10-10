package com.example.demoapi.Endpoint.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.User;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Comment.CommentRepository;
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

    @GetMapping("/search/content={content}")
    public ResponseEntity<?> getPostByContent(@PathVariable("content") String content) {
        List<Post> list = postService.findPostsByContent(content);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/post={postid}")
    public ResponseEntity<?> getCommentByPost(@PathVariable("postid") String postid) {
        List<Comment> list = postService.getCommentsByPost(postid);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/comment/{userid}/{postid}")
    public ResponseEntity<?> createComment(@PathVariable("userid") String userid,
                                           @PathVariable("postid") String postid,
                                           @RequestBody String content) {
        if (postService.createComment(userid, postid, content)){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment failed!");
    }

    @GetMapping("/comment/count/{postid}")
    public ResponseEntity<?> countReact(@PathVariable("postid") String postid){
        if(postService.countComment(postid) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.countComment(postid));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Count comment failed");
    }

    @PutMapping("/comment/edit/{commentid}")
    public ResponseEntity<?> updateComment(@RequestBody String content, @PathVariable("commentid") String commentid){
         if (postService.updateComment(content, commentid)) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update Comment failed !");
    }

    @DeleteMapping("/comment/delete/{commentid}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentid") String commentid){
        if (postService.deleteComment(commentid)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete Comment failed !");
    }

}


