package com.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookReservationRequest {

    //private LocalDateTime createdDate;

    //private LocalDateTime lastModified;

    private LocalDateTime returnedDate;

    private String title;

    private String author;

    //private BookReservationStatus status;

    //private Integer reservationId;

    //private Integer bookId;

}
