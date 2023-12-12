package com.project.repository.impl;

import com.project.domain.entity.LocationEntity;
import com.project.domain.entity.PhysicalCopyEntity;
import com.project.domain.exception.ResourceNotFoundException;
import com.project.filter.Filter;
import com.project.util.Constants;
import com.project.repository.LocationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationRepositoryImpl implements LocationRepository {

    private static final String SELECT_ALL = "SELECT l FROM LocationEntity l WHERE 1 = 1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LocationEntity> getAll(Filter... filters) {
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
                return entityManager.createQuery(dynamicQuery, LocationEntity.class)
                        .setFirstResult((filters[0].getPageNumber() - 1) * filters[0].getPageSize())
                        .setMaxResults(filters[0].getPageSize())
                        .getResultList();
            }
        }
        return entityManager.createQuery(dynamicQuery, LocationEntity.class).getResultList();
    }

    @Transactional
    @Override
    public LocationEntity save(LocationEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public LocationEntity update(LocationEntity entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public LocationEntity findById(Integer id) {
        try{
            LocationEntity location = entityManager.createQuery(Constants.SELECT_LOCATION_BY_ID,
                            LocationEntity.class).setParameter("id",id).getSingleResult();
            if (location == null){
                throw new NoResultException("No results were found");
            }
            return location;
        } catch (NoResultException e){
            throw new ResourceNotFoundException("Location with id: " + id  + " can not be found.");
        }
    }

    @Transactional
    @Override
    public LocationEntity delete(LocationEntity entity) {
        entityManager.remove(entity);
        return entity;
    }

    @Override
    public LocationEntity findByShelfName(String nameOfTheShelf) {
        try {
            return entityManager.createQuery(Constants.SELECT_LOCATION_BY_SHELF_NAME, LocationEntity.class)
                    .setParameter("nameOfTheShelf", nameOfTheShelf).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<PhysicalCopyEntity> findBooksByLocationId(Integer id) {
        try{
            List<PhysicalCopyEntity> location = entityManager.createQuery(Constants.SELECT_BOOKS_BY_LOCATION_ID,
                            PhysicalCopyEntity.class).setParameter("id",id).getResultList();
            if (location.isEmpty()){
                throw new NoResultException("No results were found");
            }
            return location;
        } catch (NoResultException e){
            return null;
        }
    }

}
