package com.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//might delete it since there isn't really any need for it

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    private LocalDateTime createdDate;

    private LocalDateTime lastModified;

    private Integer readerId;

}
