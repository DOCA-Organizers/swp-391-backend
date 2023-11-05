package com.example.demoapi.Entity.User;

import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Bookmark;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.Post.React;
import com.example.demoapi.Entity.Post.Report;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "tblUser")
@Data
public class User implements UserDetails {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String id;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "isactive")
    private boolean IsActive;

    @Column(name = "isban")
    private boolean isBan;

    @Column(name = "avt")
    private String avt;

    @JsonIgnore
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<User_Role> user_roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Collection<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Collection<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Collection<React> reacts;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Collection<Report> reports;

    @JsonIgnore
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Bookmark> bookmarks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (User_Role userRole : user_roles) {
            if (userRole.isActive()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleId().getName().toUpperCase()));
            }
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}