package com.ming.dao.imp;

import com.ming.dao.BaseRepository;
import com.ming.domain.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeptRepository extends BaseRepository<Department, Long> {

    @Select("SELECT * FROM department")
    @Results({@Result(property = "deptNo", column = "dept_no"),
            @Result(property = "deptName", column = "dept_name")})
    List<Department> findAll();

    @Select("SELECT * FROM department WHERE dept_no = #{0}")
    @Results({@Result(property = "deptNo", column = "dept_no"),
            @Result(property = "deptName", column = "dept_name")})
    Department findDeptByNo(Long id);

    @Update("UPDATE DEPARTMENT SET source = #{source},dept_name = #{deptName} WHERE dept_no =#{deptNo}")
    int updateDepartment(Department dept);

}
