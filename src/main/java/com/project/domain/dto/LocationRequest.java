package com.project.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//might delete it since there isn't really any need for it

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {

    @NotBlank(message = "The name of the shelf is required")
    private String nameOfTheShelf;

}
