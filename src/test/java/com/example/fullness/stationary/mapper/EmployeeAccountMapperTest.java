package com.example.fullness.stationary.mapper;

import com.example.fullness.stationary.entity.EmployeeAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class EmployeeAccountMapperTest {

    @Autowired
    private EmployeeAccountMapper employeeAccountMapper;

    @Test
    void selectByNameReturnAccount() {
        EmployeeAccount account = employeeAccountMapper.selectByName("fullness2");

        assertNotNull(account);
        assertAll(
            () -> assertEquals(102, account.getId()),
            () -> assertEquals(1002, account.getEmployeeId()),
            () -> assertEquals("fullness2", account.getName()),
            () -> assertEquals("f144784fe5a8f72f3bdfb9ca32f3b8e70816e4da40faa13e942460105fc35603cea2abce47573e580fe7c98d51e2a203c0b7071b967a196d701f694742456305", account.getPassword())
        );
    }
}