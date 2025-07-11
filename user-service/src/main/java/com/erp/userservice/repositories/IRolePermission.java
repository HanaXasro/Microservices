package com.erp.userservice.repositories;


import com.erp.userservice.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolePermission extends JpaRepository<RolePermission, Long> {
}
