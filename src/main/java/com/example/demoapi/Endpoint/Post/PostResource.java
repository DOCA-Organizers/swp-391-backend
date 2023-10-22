package com.example.demoapi.Endpoint.Post;

import com.example.demoapi.DTO.Post.createDTO;
import com.example.demoapi.DTO.Post.markDTO;
import com.example.demoapi.Entity.Post.*;
import com.example.demoapi.Entity.User.User;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Comment.CommentRepository;
import com.example.demoapi.Repository.Bookmark.BookmarkRepository;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Post.PostRepository;
import com.example.demoapi.Service.Bookmark.BookmarkService;
import com.example.demoapi.Service.Post.PostService;
import com.example.demoapi.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.Pipe;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @PostMapping("/react/{userid}/{id}")
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

    @PostMapping("/report/{userid}/{id}")
    public ResponseEntity<?> reportAPostOrComment(@PathVariable("userid") String userid,
            @PathVariable("id") String id, @RequestBody String msg) {
        if (postService.reportAPostOrComment(userid, id, id, msg)) {
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

    @GetMapping("/post={postid}")
    public ResponseEntity<?> getCommentByPost(@PathVariable("postid") String postid) {
        List<Comment> list = postService.getCommentsByPost(postid);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/comment/{userid}/{postid}")
    public ResponseEntity<?> createComment(@PathVariable("userid") String userid,
            @PathVariable("postid") String postid,
            @RequestBody String content) {
        if (postService.createComment(userid, postid, content)) {
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
        if (postService.deletePostByPostID(postid)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete Comment failed !");
    }

    @GetMapping("/search/category={categoryid}")
    public ResponseEntity<?> getPostbyCategory(@PathVariable("categoryid") int categoryid) {
        List<Post> list = postService.findPostsByCategory(categoryid);
        if (!list.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(list);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The category is empty!!!");
    }

    @GetMapping("/profile/post/userid={userid}")
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
    @PutMapping("/profile/post/id={postid}")
    public ResponseEntity<?> changeExchange(@PathVariable("postid") String postid) {
    boolean result = postService.changeExchange(postid);
        if (result)
            return ResponseEntity.status(HttpStatus.OK).body(result);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't change the exchange!!");
    }
    @PostMapping("/CreatePost")
    public ResponseEntity<?> createPost(@RequestBody createDTO createDTO){
        Post post = new Post();
        post.setId(UUID.randomUUID().toString());
        post.setCategory(categoryRepository.findCategoryById(createDTO.getCategoryid()));
        post.setUser(userService.SearchUserById(createDTO.getUserid()));
        post.setActive(true);
        post.setCreateTime(new Date());
        post.setContent(createDTO.getContent());
        post.setExchange(createDTO.isIsexchange());
        if (createDTO.isIsexchange()) {
            post.setSold(true);
        }
        else post.setSold(false);
        if(postService.createPost(post)){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't create Post");
    }
}
