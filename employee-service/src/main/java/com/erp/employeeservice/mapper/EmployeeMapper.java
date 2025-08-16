package com.erp.employeeservice.mapper;

import com.erp.employeeservice.dto.EmployeeCDto;
import com.erp.employeeservice.dto.EmployeeDto;
import com.erp.employeeservice.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    @Mapping( target = "id" , ignore = true)
    Employee toEntity(EmployeeCDto employeeCDto);
    EmployeeDto toDto(Employee employee);
}
