package com.ming.employee.controller;

import com.ming.employee.domain.Employee;
import com.ming.employee.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    EmpService empService;

    @GetMapping("/findEmpById/{empId}")
    public Employee findEmpById(@PathVariable("empId") Long id) {
        return empService.findEmpById(id);
    }
}
