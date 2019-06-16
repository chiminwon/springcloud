package com.ming.service;

import com.ming.dao.imp.DeptRepository;
import com.ming.domain.Department;
import com.ming.redis.lock.redis.lock.jedis.RedisLockTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

//@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
@Service
public class DeptService {

    private static final String UPDATE_SUCCESS = "Update success";
    private static final String UPDATE_FAILED = "Update failed";
    private static final String ADD_SUCCESS = "Add success";
    private static final String ADD_FAILED = "Add failed";
    private static final String DELETE_SUCCESS = "Delete success";
    private static final String DELETE_FAILED = "Delete failed";

    private String requestId = UUID.randomUUID().toString();

    @Autowired
    private DeptRepository deptRepo;

//    @Resource
//    private RedisGlobalLock redisGlobalLock;

    public String addDepartment(Department dept) {
        Long addFlag = deptRepo.saveDepartment(dept);
        System.out.println("addFlag = " + addFlag);
        if (addFlag != 0L) {
            return ADD_SUCCESS;
        } else {
            return ADD_FAILED;
        }
    }

    public List<Department> findAll() {
        return deptRepo.findAll();
    }

    public Department findDeptByNo(Long id) {
        return deptRepo.findDeptByNo(id);
    }

    public String updateDepartment(Department dept) {

        /*String key = dept.getDeptNo().toString();

        if (redisGlobalLock.lock(key)) {
            try {
                int updateFlag = 0;
                if (null != this.findDeptByNo(dept.getDeptNo())) {
                    updateFlag = deptRepo.updateDepartment(dept);
                }
                if (updateFlag == 0) {
                    return "update failed";
                } else {
                    return "update success";
                }
            } catch (Exception e) {
                throw e;
            } finally {
                // 4、释放分布式锁
                redisGlobalLock.unlock(key);
            }
        } else {
            // 如果没有获取锁
            //Ensure.that(true).isTrue("17000706");
        }*/

        Jedis jedis = new Jedis("127.0.0.1", 6380);
        jedis.auth("password");
        int updateFlag = 0;
        if (null != this.findDeptByNo(dept.getDeptNo())) {
            String lockKey = dept.getDeptNo().toString();
            boolean lockFlag = RedisLockTool.tryGetDistributedLock(jedis, lockKey, requestId, 2000);
            if (lockFlag) {
                updateFlag = deptRepo.updateDepartment(dept);
                RedisLockTool.releaseDistributedLock(jedis, lockKey, requestId);
            }
            if (updateFlag != 0) {
                return UPDATE_SUCCESS;
            }
        }
        return UPDATE_FAILED;
    }

    public String deleteDepartment(Long deptNo) {
        int deleteFlag;
        deleteFlag = deptRepo.deleteDepartment(deptNo);
        System.out.println("deleteFlag = " + deleteFlag);
        if (deleteFlag != 0) {
            return DELETE_SUCCESS;
        } else {
            return DELETE_FAILED;
        }
    }

}
