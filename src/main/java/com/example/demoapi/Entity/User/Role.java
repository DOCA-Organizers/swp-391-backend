package com.example.demoapi.Entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tblRole")
public class Role {
    @Id
    private Integer id;

    @Column(name = "name", nullable=false, unique=true)
    private String name;

    @Column(name = "type")
    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "roleId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<User_Role> user_roles;
}
