package com.example.fullness.stationary.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * DBに保存されている社員アカウントテーブルを定義
 * 
 */
@Data
public class EmployeeAccount implements Serializable {
    private Integer id;
    private Integer employeeId;
    private String name;
    private String password;
    private Employee employee;

    // public EmployeeAccount(Integer employeeId, String name, String password) {

    // this.employeeId = employeeId;
    // this.name = name;
    // this.password = password;
    // }

}
