package com.example.fullness.stationary.mapper;

import com.example.fullness.stationary.entity.EmployeeAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeAccountMapper {
    
    EmployeeAccount selectByName(String name);
}