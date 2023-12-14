package com.project.repository;

import com.project.domain.entity.ReaderEntity;
import com.project.filter.Filter;

import java.util.List;

public interface ReaderRepository extends BaseRepository<ReaderEntity,Integer> {

    ReaderEntity findByIdAndDeletedValueFalse(Integer id);

    //will probably delete
    ReaderEntity findByIdAndDeletedValueTrue(Integer id);

    ReaderEntity softDelete(ReaderEntity entity);

    //will probably delete
    ReaderEntity restore(ReaderEntity entity);

    ReaderEntity findByEmail(String email);

    List<ReaderEntity> getAll(Filter... filters);

}
