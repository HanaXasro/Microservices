package com.erp.employeeservice.repository;

import com.erp.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployee extends JpaRepository<Employee ,Long> {
    boolean existsByPhone(String phone);

}
