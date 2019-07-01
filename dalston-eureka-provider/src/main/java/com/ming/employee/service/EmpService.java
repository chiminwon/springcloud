package com.ming.employee.service;

import com.ming.employee.dao.imp.EmpRepository;
import com.ming.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmpService {

    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    public Employee findEmpById(Long id) {
        Object object = redisTemplate.opsForValue().get(String.valueOf(id));
        //缓存查询命中
        if (null != object) {
            System.out.println("返回了缓存数据，主键id： " + id);
            return (Employee) object;
        }
        //查询数据库
        Employee employee = empRepository.findEmpById(id);
        if (null != employee) {
            System.out.println("进行了数据库查询，主键id： " + id);
            //防止数据库“雪崩”
            if (employee.getEmpAge() == 18) {
                Random random = new Random();//随机因子
                int time = 3600 + random.nextInt(3600);
                redisTemplate.opsForValue().set(String.valueOf(id), employee, time, TimeUnit.SECONDS);
            } else if (employee.getEmpAge() == 28) {
                Random random = new Random();
                int time = 600 + random.nextInt(600);
                redisTemplate.opsForValue().set(String.valueOf(id), employee, time, TimeUnit.SECONDS);
            } else if (employee.getEmpAge() == 78) {
                //设置缓存永不过期，防止“缓存击穿”
                redisTemplate.opsForValue().set(String.valueOf(id), employee, -1, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(String.valueOf(id), employee, 60, TimeUnit.MINUTES);
            }
        } else {
            System.out.println("进行了数据库查询，空值，主键id： " + id);
            //将空值放入缓存，防止“缓存穿透”
            redisTemplate.opsForValue().set(String.valueOf(id), null, 60, TimeUnit.SECONDS);
        }
        return employee;
    }
}
