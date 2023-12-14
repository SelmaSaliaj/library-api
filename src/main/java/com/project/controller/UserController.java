package com.project.controller;

import com.project.domain.dto.UserDTO;
import com.project.domain.dto.UserRequest;
import com.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public void save(@RequestBody @Valid UserRequest user){
        userService.save(user);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public void update(@PathVariable Integer id, @RequestParam String username, @RequestParam String password){
        userService.update(id, username, password);
    }

    @PatchMapping("/changeStatus/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateAuthority(@PathVariable Integer id){
        userService.updateAuthority(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public void delete(@PathVariable("id") Integer id){
        userService.delete(id);
    }

    @GetMapping("/{pageNumber}/{size}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> getAll(@PathVariable int pageNumber, @PathVariable int size){
        return userService.getAllUsers(pageNumber,size);
    }

}
