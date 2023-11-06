package com.example.demoapi.Repository.PostImg;

import com.example.demoapi.Entity.Post.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyPostImgRepository extends JpaRepository<PostImg,String> {
}
