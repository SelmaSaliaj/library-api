package com.project.repository.impl;

import com.project.domain.entity.UserEntity;
import com.project.filter.Filter;
import com.project.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_QUERY = "SELECT u FROM UserEntity u WHERE u.id = :id";

    private static final String SELECT_ALL = "SELECT u FROM UserEntity u WHERE 1 = 1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> getAll(Filter... filters) {
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
                return entityManager.createQuery(dynamicQuery, UserEntity.class)
                        .setFirstResult((filters[0].getPageNumber() - 1) * filters[0].getPageSize())
                        .setMaxResults(filters[0].getPageSize())
                        .getResultList();
            }
        }
        return entityManager.createQuery(dynamicQuery, UserEntity.class).getResultList();
    }

    @Transactional
    @Override
    public UserEntity save(UserEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public UserEntity update(UserEntity entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public UserEntity findById(Integer id) {
        return entityManager.createQuery(FIND_QUERY,UserEntity.class)
                .setParameter("id",id).getSingleResult();
    }

    @Transactional
    @Override
    public UserEntity delete(UserEntity entity) {
        entityManager.remove(entity);
        return entity;
    }

}
