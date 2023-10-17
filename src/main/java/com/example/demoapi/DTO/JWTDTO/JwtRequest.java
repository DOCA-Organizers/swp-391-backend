package com.example.demoapi.DTO.JWTDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtRequest {
    private String userName;
    private String password;
}
