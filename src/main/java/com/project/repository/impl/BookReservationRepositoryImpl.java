package com.project.repository.impl;

import com.project.domain.entity.BookReservationEntity;
import com.project.domain.exception.ResourceNotFoundException;
import com.project.filter.Filter;
import com.project.repository.BookReservationRepository;
import com.project.util.Constants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookReservationRepositoryImpl implements BookReservationRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<BookReservationEntity> getAll(Filter... filters) {
        return null;
    }

    @Override
    public BookReservationEntity save(BookReservationEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public BookReservationEntity update(BookReservationEntity entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public BookReservationEntity findById(Integer id) {
        return entityManager.createQuery(Constants.SELECT_BOOK_RESERVATION_BY_ID,BookReservationEntity.class)
                .setParameter("id",id).getSingleResult();
    }

    @Override
    public BookReservationEntity delete(BookReservationEntity entity) {
        entity = softDelete(entity);
        entityManager.remove(entity);
        return entity;
    }

    @Override
    public BookReservationEntity findByIdAndDeletedValueFalse(Integer id) {
        try{
            BookReservationEntity bookReservation = entityManager.createQuery(Constants.SELECT_RESERVATION_BY_ID_AND_DELETED_FALSE,
                    BookReservationEntity.class).setParameter("id",id).getSingleResult();
            if(bookReservation == null){
                throw new NoResultException("No results were found");
            }
            return bookReservation;
        } catch (NoResultException e){
            throw new ResourceNotFoundException("Book reservation with id: " + id  +
                    " can not be found within the existing book reservations.");
        }
    }

    @Override
    public BookReservationEntity softDelete(BookReservationEntity entity) {
        entity.setDeleted(true);
        return update(entity);
    }

    //will probably delete method
    @Override
    public BookReservationEntity restore(BookReservationEntity entity) {
        entity.setDeleted(false);
        return entity;
    }

    @Override
    public List<BookReservationEntity> findBookReservationsByReservationId(Integer id) {
        try{
            List<BookReservationEntity> bookReservationEntities = entityManager
                    .createQuery(Constants.SELECT_BOOK_RESERVATION_BY_RESERVATION_ID, BookReservationEntity.class)
                    .setParameter("id",id).getResultList();
            if (bookReservationEntities.isEmpty()){
                throw new NoResultException("No results were found");
            }
            return bookReservationEntities;
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<BookReservationEntity> findBookReservationsByReservationIdAndStatusNotReturned(Integer id) {
        try{
            List<BookReservationEntity> bookReservationEntities = entityManager
                    .createQuery(Constants.SELECT_BOOK_RESERVATION_BY_RESERVATION_ID_AND_STATUS_NOT_RETURNED,
                    BookReservationEntity.class).setParameter("id",id).getResultList();
            if (bookReservationEntities.isEmpty()){
                throw new NoResultException("No results were found");
            }
            return bookReservationEntities;
        } catch (NoResultException e){
            return null;
        }
    }
}
