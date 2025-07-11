package com.erp.userservice.repositories;

import com.erp.userservice.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermission extends JpaRepository<Permission, Long> {
    boolean existsByTitle(String title);
}
