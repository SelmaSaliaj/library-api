package com.project.repository;

import com.project.domain.entity.ReservationEntity;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends BaseRepository<ReservationEntity,Integer> {

    ReservationEntity findByIdAndDeletedValueFalse(Integer id);

    //will probably delete
    ReservationEntity findByIdAndDeletedValueTrue(Integer id);

    ReservationEntity softDelete(ReservationEntity entity);

    //will probably delete
    ReservationEntity restore(ReservationEntity entity);

    List<ReservationEntity> findReservationsByReaderId(Integer id);

    List<ReservationEntity> findReservationsByIdAndLocalDate(Integer id, LocalDate createdDate);

}
