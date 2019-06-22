package com.ming.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Department implements Serializable{

    private Long deptNo;
    private String deptName;
    private String source;
}
