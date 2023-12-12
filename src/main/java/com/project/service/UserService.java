package com.project.service;

import com.project.domain.dto.UserDTO;
import com.project.domain.dto.UserRequest;
import com.project.filter.Filter;

import java.util.List;

public interface UserService{

    UserDTO findById(Integer id);

    void save(UserRequest request);

    void update(UserDTO request);

    void delete(Integer id);

    List<UserDTO> getAllEBooks(Filter... filters);

}
