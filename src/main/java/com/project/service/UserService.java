package com.project.service;

import com.project.domain.dto.UserDTO;
import com.project.domain.dto.UserRequest;

import java.util.List;

public interface UserService{

    UserDTO findById(Integer id);

    void save(UserRequest request);

    //void update(Integer id,UserRequest request);

    void update(Integer id,String username, String password);

    //void updateAuthority(Integer id, ReaderRequest request);

    void updateAuthority(Integer id);

    void delete(Integer id);

    List<UserDTO> getAllUsers(int pageNumber, int pageSize);

}
