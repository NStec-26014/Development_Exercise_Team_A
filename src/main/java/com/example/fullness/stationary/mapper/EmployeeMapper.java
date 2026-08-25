package com.example.fullness.stationary.mapper;

import com.example.fullness.stationary.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
    
    Employee selectById(Integer id);
}