package com.project.repository;

import com.project.domain.entity.PhysicalCopyEntity;
import com.project.filter.Filter;

import java.util.List;

public interface PhysicalCopyRepository extends BaseRepository<PhysicalCopyEntity,Integer> {

    List<PhysicalCopyEntity> findByTitle(String title);

    List<PhysicalCopyEntity> findByAuthor(String author);

    PhysicalCopyEntity findByTitleAndAuthor(String title, String author);

    List<PhysicalCopyEntity> getAll(Filter... filters);

}
