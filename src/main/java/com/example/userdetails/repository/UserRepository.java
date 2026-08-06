package com.example.userdetails.repository;

import com.example.userdetails.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByNameContainingIgnoreCaseAndCityContainingIgnoreCase(

            String name,

            String city,

            Pageable pageable

    );

    Page<User> findByStatus(Boolean status, Pageable pageable);

}