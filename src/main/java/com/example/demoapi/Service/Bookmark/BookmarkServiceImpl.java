package com.example.demoapi.Service.Bookmark;

import com.example.demoapi.Entity.Post.Bookmark;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Repository.Bookmark.BookmarkRepository;
import com.example.demoapi.Repository.Post.PostRepository;
import com.example.demoapi.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService{

    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;


    @Override
    public Bookmark findBookmark(String userid, String postid) {
        try{
            return bookmarkRepository.findBookmarkByUseridAndPostid(userid, postid);
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
    public boolean markthePost(String userid, String postid) {
        try{
            Bookmark bm = new Bookmark();
            bm.setUserId(userRepository.findUserById(userid));
            bm.setPostId(postRepository.findPostById(postid));
            bm.setCreateTime(new Date());
            bm.setIsactive(true);
            Bookmark check = bookmarkRepository.findBookmarkByUseridAndPostidUp(userid,postid);
            if (check!=null){
                bookmarkRepository.changeBookmarkStatus(check.getId());
            }
            else bookmarkRepository.save(bm);
            return true;
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
