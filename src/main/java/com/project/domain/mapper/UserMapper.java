package com.project.domain.mapper;

import com.project.domain.dto.UserDTO;
import com.project.domain.dto.UserRequest;
import com.project.domain.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserRequest userRequest){
        UserEntity toReturn = new UserEntity();
        toReturn.setUsername(userRequest.getUsername());
        toReturn.setPassword(userRequest.getPassword());
        toReturn.setAuthorities(userRequest.getAuthorities());
        return toReturn;
    }

    public static UserDTO toDTO(UserEntity user){
        UserDTO toReturn = new UserDTO();
        toReturn.setUsername(user.getUsername());
        toReturn.setAuthorities(user.getAuthorities());
        toReturn.setReader(ReaderMapper.toDTO(user.getReader()));
        toReturn.setReader(ReaderMapper.toDTO(user.getReader()));
        return toReturn;
    }

    public static UserEntity toEntity(UserDTO user){
        UserEntity toReturn = new UserEntity();
        toReturn.setId(user.getId());
        toReturn.setUsername(user.getUsername());
        toReturn.setAuthorities(user.getAuthorities());
        toReturn.setReader(ReaderMapper.toEntity(user.getReader()));
        toReturn.setReader(ReaderMapper.toEntity(user.getReader()));
        return toReturn;
    }

}
