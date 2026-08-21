package com.example.fullness.stationary.mapper;

import com.example.fullness.stationary.entity.EmployeeAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeAccountMapper {
    
    EmployeeAccount selectByName(@Param("name") String name);
}