package com.example.fullness.stationary.entity;

import lombok.Data;

import java.io.Serializable;
 
@Data
public class EmployeeAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer employeeId;
    private String name;
    private String password;

}
