package com.project.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO{

    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "Authority is required")
    private String authorities;

    private ReaderDTO reader;

}
