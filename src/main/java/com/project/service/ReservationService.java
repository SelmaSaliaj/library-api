package com.project.service;

import com.project.domain.dto.ReaderDTO;
import com.project.domain.dto.ReservationDTO;
import com.project.domain.entity.ReservationEntity;
import com.project.filter.Filter;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService{

    ReservationDTO findById(Integer id);

    ReservationDTO findByIdAndDeletedValueFalse(Integer id);

    ReservationDTO findByIdAndDeletedValueTrue(Integer id);

    List<ReservationDTO> findByIdAndLocalDate(Integer id, LocalDateTime date);

    ReservationDTO save(Integer readerId);

    ReservationDTO update(Integer id, LocalDateTime modified_date);

    ReservationDTO softDelete(Integer id);

    ReservationDTO restore(Integer id);

    ReservationDTO delete(Integer id);

    List<ReservationEntity> findReservationsByReaderId(Integer id);

    List<ReaderDTO> getAllReservations(Filter... filters);

}
