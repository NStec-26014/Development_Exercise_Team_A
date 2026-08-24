package com.example.fullness.stationary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;

@Mapper
public interface EmployeeAccountMapper {

    /**
     * Employeeテーブルの中でアカウント登録情報がEmployeeAccountテーブルにない社員を全件検索
     * 
     * @return Employee
     */
    boolean existsByAccountName(String accountName);

    List<Employee> findAllByNameIsNull();

    String findEmployeeNameByEmployeeId(Integer employeeId);

    int inputEmployeeAccount(EmployeeAccount employeeAccount);

}
