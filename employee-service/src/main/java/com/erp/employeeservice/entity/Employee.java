package com.erp.employeeservice.entity;

import com.erp.commonlib.entityHelper.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first-name",nullable = false, length = 50)
    private String firstName;
    @Column(name = "middle_name",nullable = false, length = 50)
    private String middleName;
    @Column(name = "last_name",nullable = false, length = 50)
    private String lastName;
    private String email;
    @Column(nullable = false, length = 50 ,  unique = true)
    private String phone;
}
