package com.example.fullness.stationary.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * DBに保存されている社員アカウントテーブルを定義
 * 
 */
@Data
public class EmployeeAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer employeeId;
    private String name;
    private String password;
    private Employee employee;

}
