package com.example.demoapi.Repository.Category;

import com.example.demoapi.Entity.Post.Category;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MyCategoryRepository extends JpaRepository<Category,Integer> {
    Category findCategoryById(int id);

    // @Query(value = "",nativeQuery = true)
    // @Transactional
    //@Modifying


}
