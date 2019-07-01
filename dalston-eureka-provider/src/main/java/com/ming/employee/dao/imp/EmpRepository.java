package com.ming.employee.dao.imp;

import com.ming.employee.domain.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EmpRepository {

    //拼接符${} 占位符#{}
    @Select("select * from employee where emp_id = #{empId}")
    @Results({
            @Result(id = true, property = "empId", column = "emp_Id"),
            @Result(property = "empName", column = "emp_name"),
            @Result(property = "empAge", column = "emp_age"),
            @Result(property = "department", column = "emp_dept_no",
                one = @One(select = "com.ming.department.dao.imp.DeptRepository.findDeptByNo"))
    })
    Employee findEmpById(Long empId);
}
