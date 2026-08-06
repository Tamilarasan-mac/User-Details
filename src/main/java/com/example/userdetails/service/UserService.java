package com.example.userdetails.service;

import com.example.userdetails.model.User;
import com.example.userdetails.repository.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    private static final Logger log =
            LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // PAGINATION
    public Page<User> getAllUsers(Pageable pageable){

        log.info("Fetching users with pagination");

        return repository.findAll(pageable);
    }

    public Page<User> filterUsers(
            String name,
            String city,
            Boolean status,
            Pageable pageable){

        log.info("Filtering users");

        if(status != null){
            return repository.findByStatus(status, pageable);
        }

        String nameFilter = name == null ? "" : name;
        String cityFilter = city == null ? "" : city;

        return repository
                .findByNameContainingIgnoreCaseAndCityContainingIgnoreCase(
                        nameFilter,
                        cityFilter,
                        pageable
                );
    }

    // SAVE
    public User save(@Valid User user) {
        log.info("Saving user: {}", user.getName());
        return repository.save(user);
    }

    // GET ALL
    public List<User> getAll(){
        log.info("Fetching all users");
        return repository.findAll();
    }

    // GET BY ID
    public User getById(Long id){
        log.debug("Fetching user by id: {}", id);
        return repository.findById(id).orElse(null);
    }

    // UPDATE
    public User update(Long id, User user){

        log.info("Updating user with id: {}", id);

        User existing = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new RuntimeException("User not found");
                });

        existing.setName(user.getName());
        existing.setCity(user.getCity());
        existing.setStatus(user.getStatus());
        existing.setEmail(user.getEmail());

        return repository.save(existing);
    }

    // DELETE
    public void delete(Long id){
        log.warn("Deleting user with id: {}", id);
        repository.deleteById(id);
    }
}