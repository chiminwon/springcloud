package com.ming.employee.domain;

import com.ming.department.domain.Department;
import lombok.Data;

@Data
public class Employee {

    private Long empId;
    private String empName;
    private int empAge;
    private Department department;
}
