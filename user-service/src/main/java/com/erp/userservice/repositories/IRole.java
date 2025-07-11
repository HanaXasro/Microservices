package com.erp.userservice.repositories;

import com.erp.userservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRole  extends JpaRepository<Role, Long> {
}
