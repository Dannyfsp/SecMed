package com.secsystem.emr.user.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUpResponse {
    private String email;
    private Integer id;
    private String role;
}
