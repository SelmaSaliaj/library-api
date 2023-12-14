package com.project.service;

import com.project.domain.dto.ReservationDTO;
import com.project.filter.Filter;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService{

    ReservationDTO findById(Integer id);

    ReservationDTO findByIdAndDeletedValueFalse(Integer id);

    ReservationDTO findByIdAndDeletedValueTrue(Integer id);

    ReservationDTO save(Integer readerId);

    ReservationDTO update(Integer id, LocalDateTime modified_date);

    ReservationDTO update(ReservationDTO reservation);

    ReservationDTO softDelete(Integer id);

    ReservationDTO restore(Integer id);

    ReservationDTO delete(Integer id);

    List<ReservationDTO> findReservationsByReaderId(Integer id);

    ReservationDTO findReservationsByReaderIdAndLocalDate(Integer id, LocalDateTime date);

    List<ReservationDTO> findByLocalDate(LocalDateTime date);

    List<ReservationDTO> getAllReservations(Filter... filters);

}
