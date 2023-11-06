package com.example.demoapi.DTO.Post;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class exchangeItem {
    private String pet_item;
    private String pet_type;
    private String img;
    private String description;
    private String price;

}
