package com.project.service;

import com.project.domain.dto.BookReservationDTO;
import com.project.domain.dto.BookReservationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BookReservationService {

    BookReservationDTO findById(Integer id);

    BookReservationDTO findByIdAndDeletedValueFalse(Integer id);

    void save(Integer readerId, BookReservationRequest request);

    void update(Integer id, LocalDateTime newToReturnDateTime);
    //void update(Integer id, String newToReturnDateTime);

    void update(BookReservationDTO bookReservation);

    void delete(Integer id);

    void softDelete(Integer id);

    void updateStatusToReturned(Integer id);

    List<BookReservationDTO> findBookReservationsByReservationId(Integer id);

    List<BookReservationDTO> findBookReservationsByReservationIdAndStatusNotReturned(Integer id);

    List<BookReservationDTO> findBookReservationsByBookId(Integer id);

    List<BookReservationDTO> findBookReservationsByIdAndLocalDate(Integer id, LocalDateTime createdDate);

    List<BookReservationDTO> findBookReservationsByLocalDate(LocalDateTime createdDate);

    Page<BookReservationDTO> getAllBookReservations(Pageable pageable);

}
