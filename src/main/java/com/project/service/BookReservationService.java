package com.project.service;

import com.project.domain.dto.BookReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookReservationService {

    BookReservationDTO findById(Integer id);

    void save(BookReservationDTO request);

    void update(BookReservationDTO request);

    void delete(Integer id);

    Page<BookReservationDTO> getAllBookReservations(Pageable pageable);

}
