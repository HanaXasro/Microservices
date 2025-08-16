package com.erp.employeeservice.service.imp;

import com.erp.commonlib.exceptions.DuplicatePhoneException;
import com.erp.employeeservice.dto.EmployeeCDto;
import com.erp.employeeservice.dto.EmployeeDto;
import com.erp.employeeservice.entity.Employee;
import com.erp.employeeservice.mapper.EmployeeMapper;
import com.erp.employeeservice.repository.IEmployee;
import com.erp.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private IEmployee employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeDto CreateEmployee(EmployeeCDto employeeDto) {
        if (employeeRepository.existsByPhone(employeeDto.getPhone())) {
            throw new DuplicatePhoneException("Phone number already exists: " + employeeDto.getPhone());
        }

        // 2. Map DTO to Entity
        Employee employee = employeeMapper.toEntity(employeeDto);

        // 3. Save entity
        Employee savedEmployee = employeeRepository.save(employee);

        // 4. Map Entity to Response DTO
        return employeeMapper.toDto(savedEmployee);
    }
}
