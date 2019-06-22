package com.ming.dao.imp;

import com.ming.dao.BaseRepository;
import com.ming.domain.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface DeptRepository extends BaseRepository<Department, Long> {

    /**
     * @param dept
     * @return 返回自增主键的情况
     */
    @CachePut(value = "deptCache", keyGenerator = "allenKeyGenerator")
    @Insert({"INSERT INTO department(dept_name, source) VALUES(#{deptName}, #{source})"})
    @Options(useGeneratedKeys = true, keyProperty = "deptNo", keyColumn = "dept_no")
    //@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "deptNo", resultType = Long.class, before = false)
    Long saveDepartment(Department dept);

    @Select("SELECT * FROM department")
    @Results({@Result(property = "deptNo", column = "dept_no"),
            @Result(property = "deptName", column = "dept_name")})
    List<Department> findAll();

    @Cacheable(value = "deptCache", keyGenerator = "allenKeyGenerator")
    @Select("SELECT * FROM department WHERE dept_no = #{0}")
    @Results({@Result(property = "deptNo", column = "dept_no"),
            @Result(property = "deptName", column = "dept_name")})
    Department findDeptByNo(Long deptNo);

    @Update("UPDATE department SET source = #{source},dept_name = #{deptName} WHERE dept_no =#{deptNo}")
    int updateDepartment(Department dept);

    @CacheEvict(value = "deptCache", keyGenerator = "allenKeyGenerator")
    @Delete({"DELETE FROM department WHERE dept_no = #{0}"})
    int deleteDepartment(Long deptNo);

}
