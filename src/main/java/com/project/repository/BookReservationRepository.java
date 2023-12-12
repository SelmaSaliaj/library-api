package com.project.repository;

import com.project.domain.entity.BookReservationEntity;

import java.util.List;

public interface BookReservationRepository extends BaseRepository<BookReservationEntity,Integer> {

    BookReservationEntity findByIdAndDeletedValueFalse(Integer id);

    BookReservationEntity softDelete(BookReservationEntity entity);

    //will probably delete
    BookReservationEntity restore(BookReservationEntity entity);

    List<BookReservationEntity> findBookReservationsByReservationId(Integer id);

    List<BookReservationEntity> findBookReservationsByReservationIdAndStatusNotReturned(Integer id);

}
