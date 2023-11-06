package com.example.demoapi.DTO.Post;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class exchangePet {
    private String type;
    private String name;
    private boolean gender;
    private int age;
    private String img;
    private String price;
    private String description;
}
