package com.project.controller;

import com.project.domain.dto.UserDTO;
import com.project.domain.dto.UserRequest;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public void save(@RequestBody UserRequest userDTO){
        userService.save(userDTO);
    }

    @PutMapping
    public void update(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        userService.delete(id);
    }


}
