package com.secsystem.emr.user.dto.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private String token;
//    private String refreshToken;
    private String email;
}
