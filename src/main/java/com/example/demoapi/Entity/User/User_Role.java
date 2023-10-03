package com.example.demoapi.Entity.User;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tblUser_Role")
@Data
public class User_Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "roleid")
    private Role roleId;

    @Column(name = "datestart", nullable = false)
    private Date dateStart;

    @Column(name = "dateend")
    private Date dateEnd;

    @Column(name = "status")
    private boolean status;

}
