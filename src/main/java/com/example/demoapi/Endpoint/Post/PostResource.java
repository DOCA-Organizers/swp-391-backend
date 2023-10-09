package com.example.demoapi.Endpoint.Post;

import com.example.demoapi.Entity.Post.Bookmark;
import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Repository.Bookmark.BookmarkRepository;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Service.Bookmark.BookmarkService;
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
    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/search/category={categoryid}")
    public ResponseEntity<?> getPostbyCategory(@PathVariable("categoryid") int categoryid) {
        List<Post> list = postService.findPostsByCategory(categoryid);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/profile/post/userid={userid}&post")
    public ResponseEntity<?> getPostsbyUser(@PathVariable("userid") String userid) {
        List<Post> list = postService.findPostsByUser(userid);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/search/post/key={keyword}")
    public ResponseEntity<?> seachpostbyTitle(@PathVariable("keyword") String keyword) {
        List<Post> list = postService.searchPostsByTitle(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/bookmark/userid={userid}&postid={postid}")
    public ResponseEntity<?> showbookmarkpost(@PathVariable("userid") String userid, @PathVariable("postid") String postid) {
        Bookmark mark = bookmarkService.findBookmark(userid, postid);
    return ResponseEntity.status(HttpStatus.OK).body(mark);
    }

    @GetMapping("/bookmark/List/userid={userid}")
    public ResponseEntity<?> showlistbookmarkposts(@PathVariable("userid") String userid){
        List<Post> list = postService.showBookmarkPosts(userid);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    //hihihi
}

