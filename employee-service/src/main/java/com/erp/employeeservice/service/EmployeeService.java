package com.erp.employeeservice.service;

import com.erp.employeeservice.dto.EmployeeCDto;
import com.erp.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto CreateEmployee(EmployeeCDto employeeDto);
}
