package com.erp.userservice.repositories;

import com.erp.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUser extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
