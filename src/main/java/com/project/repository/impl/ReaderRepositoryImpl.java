package com.project.repository.impl;

import com.project.domain.entity.ReaderEntity;
import com.project.domain.exception.ResourceNotFoundException;
import com.project.filter.Filter;
import com.project.util.Constants;
import com.project.repository.ReaderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReaderRepositoryImpl implements ReaderRepository {

    private static final String SELECT_ALL = "SELECT r FROM ReaderEntity r WHERE 1 = 1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ReaderEntity save(ReaderEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public ReaderEntity update(ReaderEntity entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public ReaderEntity findById(Integer id) {
        try{
            ReaderEntity reader = entityManager.createQuery(Constants.SELECT_READER_BY_ID,ReaderEntity.class)
                    .setParameter("id",id).getSingleResult();
            if(reader == null){
                throw new NoResultException("No results were found");
            }
            return reader;
        } catch (NoResultException e){
            throw new ResourceNotFoundException("Reader with id: " + id  + " can not be found.");
        }
    }

    @Override
    public ReaderEntity delete(ReaderEntity entity) {
        entityManager.remove(entity);
        return entity;
    }

    @Override
    public ReaderEntity findByIdAndDeletedValueFalse(Integer id) {
        try{
            ReaderEntity reader = entityManager.createQuery(Constants.SELECT_READER_BY_ID_AND_DELETED_FALSE,
                            ReaderEntity.class).setParameter("id",id).getSingleResult();
            if(reader == null){
                throw new NoResultException("No results were found");
            }
            return reader;
        } catch (NoResultException e){
            throw new ResourceNotFoundException("Reader with id: " + id  +
                    " can not be found within the existing readers.");
        }
    }

    @Override
    public ReaderEntity findByIdAndDeletedValueTrue(Integer id) {
        try{
            ReaderEntity reader = entityManager.createQuery(Constants.SELECT_READER_BY_ID_AND_DELETED_TRUE,
                            ReaderEntity.class).setParameter("id",id).getSingleResult();
            if(reader == null){
                throw new NoResultException("No results were found");
            }
            return reader;
        } catch (NoResultException e){
            throw new ResourceNotFoundException("Reader with id: " + id  +
                    " can not be found with deleted value true.");
        }
    }

    @Override
    public ReaderEntity softDelete(ReaderEntity entity) {
        entity.setDeleted(true);
        return update(entity);
    }

    //@Transactional
    //will probably delete method
    @Override
    public ReaderEntity restore(ReaderEntity entity) {
        entity.setDeleted(false);
        return entity;
    }

    @Override
    public ReaderEntity findByEmail(String email) {
        try{
            ReaderEntity reader = entityManager.createQuery(Constants.SELECT_READER_BY_EMAIL,ReaderEntity.class)
                    .setParameter("email",email).getSingleResult();
            if(reader == null){
                throw new NoResultException("No results were found");
            }
            return reader;
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<ReaderEntity> getAll(Filter... filters) {
        String dynamicQuery = SELECT_ALL;

        if (filters != null) {
            if (filters[0].getValue() != null) {
                dynamicQuery += "AND c." + filters[0].getField() + " " +
                        filters[0].getOperator() + " '%" + filters[0].getValue() + "%' ";
            }
            if (filters[0].getSort() != null) {
                dynamicQuery += "ORDER BY c." + filters[0].getField() + " " + filters[0].getSort();
            }
            if (filters[0].getPageSize() != null && filters[0].getPageNumber() != null) {
                return entityManager.createQuery(dynamicQuery, ReaderEntity.class)
                        .setFirstResult((filters[0].getPageNumber() - 1) * filters[0].getPageSize())
                        .setMaxResults(filters[0].getPageSize())
                        .getResultList();
            }
        }
        return entityManager.createQuery(dynamicQuery, ReaderEntity.class).getResultList();
    }

}
