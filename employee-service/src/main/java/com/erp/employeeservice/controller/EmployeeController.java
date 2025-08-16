package com.erp.employeeservice.controller;

import com.erp.employeeservice.dto.EmployeeCDto;
import com.erp.employeeservice.dto.EmployeeDto;
import com.erp.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
@Tag(name = "employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<EmployeeDto> login(@Valid @RequestBody EmployeeCDto employeeCDto) {
        return ResponseEntity.ok(employeeService.CreateEmployee(employeeCDto));
    }
}
