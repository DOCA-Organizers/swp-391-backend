package com.example.demoapi.Repository.User;

import com.example.demoapi.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MyUserRepository {
}
