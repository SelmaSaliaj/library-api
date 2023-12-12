package com.project.service.impl;

import com.project.domain.dto.UserDTO;
import com.project.domain.dto.UserRequest;
import com.project.domain.entity.UserEntity;
import com.project.domain.mapper.UserMapper;
import com.project.filter.Filter;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDTO findById(Integer id) {
        return UserMapper.toDTO(repository.findById(id));
    }

    @Override
    public void save(UserRequest request) {
        UserMapper.toDTO(repository.save(UserMapper.toEntity(request)));
    }

    @Override
    public void update(UserDTO request) {
        repository.update(UserMapper.toEntity(request));
    }

    @Override
    public void delete(Integer id) {
        repository.delete(repository.findById(id));
    }

    @Override
    public List<UserDTO> getAllEBooks(Filter... filters) {
        List<UserEntity> users = repository.getAll(filters);
        return users.stream()
                .map(u -> UserMapper.toDTO(u))
                .collect(Collectors.toList());
    }


}
