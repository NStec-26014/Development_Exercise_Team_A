package com.example.fullness.stationary.mapper;

import com.example.fullness.stationary.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    void selectByIdReturnEmployee() {
        Employee employee = employeeMapper.selectById(1002);

        assertNotNull(employee);
        assertAll(
            () -> assertEquals(1002, employee.getId()),
            () -> assertEquals(2, employee.getDepartmentId()),
            () -> assertEquals("山田太郎", employee.getName()),
            () -> assertEquals("ヤマダタロウ", employee.getName_kana())
        );
    }
}