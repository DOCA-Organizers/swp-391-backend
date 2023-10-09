package com.example.demoapi.Service.Bookmark;

import com.example.demoapi.Entity.Post.Bookmark;
import com.example.demoapi.Entity.Post.Post;

import java.util.List;

public interface BookmarkService {
    Bookmark findBookmark(String userid,String postid);
}
