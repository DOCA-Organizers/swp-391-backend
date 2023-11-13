package com.example.demoapi.Repository.Category;

import com.example.demoapi.Entity.Post.Category;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyCategoryRepository extends JpaRepository<Category,Integer> {
    Category findCategoryById(int id);

    @Query(value = "select * from tblCategory",nativeQuery = true)
    List<Category> getCategories();

    @Query (value = "select count(id) from tblPost where categoryid = ?",nativeQuery = true)
    int getNumberOfPostbyCate(int cateid);

    @Query (value = "select count(id) from tblcomment where postid in( select id from tblPost where categoryid= ?)",nativeQuery = true)
    int getNumberofCommentbyCate(int cateid);
}
