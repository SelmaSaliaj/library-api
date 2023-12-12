package com.project.domain.dto;

import com.project.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String username;

    private String password;

    private String authorities;

    private UserEntity reader;

}
