package com.example.userdetails.controller;

import com.example.userdetails.model.User;
import com.example.userdetails.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public User create(@Valid @RequestBody User user){
        log.info("Creating new user");
        return service.save(user);
    }

    // GET ALL WITH PAGINATION
    @GetMapping
    public Page<User> getAll(
            @PageableDefault(page = 0, size = 5) Pageable pageable){

        log.info("Fetching users with pagination");

        return service.getAllUsers(pageable);
    }

    // GET ALL FILTER
    @GetMapping("/filter")
    public Page<User> filterUsers(

            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Boolean status,
            @PageableDefault(size = 5) Pageable pageable){

        log.info("Filtering users API called");

        return service.filterUsers(name, city, status, pageable);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){
        log.debug("Fetching user by id: {}", id);
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public User update(@PathVariable Long id,
                       @Valid @RequestBody User user){

        log.info("Updating user with id: {}", id);
        return service.update(id, user);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        log.warn("Deleting user with id: {}", id);
        service.delete(id);
    }
}