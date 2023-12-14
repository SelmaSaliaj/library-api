package com.project.repository;

import com.project.domain.entity.UserEntity;

import java.util.List;

public interface UserRepository extends BaseRepository<UserEntity,Integer> {

    UserEntity findByUsername(String username);

    List<UserEntity> getAll(int pageNumber, int pageSize);

}
