package com.ming.service;

import com.ming.dao.imp.DeptRepository;
import com.ming.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {

    @Autowired
    private DeptRepository deptRepo;

    public List<Department> findAll(){
        return deptRepo.findAll();
    }

    public Department findDeptByNo(Long id){
        return deptRepo.findDeptByNo(id);
    }

    public String updateDepartment(Department dept){
        int updateFlag = 0;
        if(null != this.findDeptByNo(dept.getDeptNo())){
            updateFlag = deptRepo.updateDepartment(dept);
        }
        if(updateFlag == 0){
            return "update failed";
        }else{
            return "update success";
        }
    }

}
