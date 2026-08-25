package com.example.fullness.stationary.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class EmployeeAccountMapperTest {

    @Autowired
    EmployeeAccountMapper employeeAccountMapper;

    @Test
    void existsByAccountNameTest_OK() {

        String accountName = "fullness2";
        boolean actual = employeeAccountMapper.existsByAccountName(accountName);
        assertEquals(true, actual);
    }
    // ほかのメソッドの単体テストは後で実装
    // @Test
    // void findAllByNameIsNullTest_OK() {

    // Employee employee = new Employee();
    // employee.setId(1001);
    // employee.setName("フルネス太郎");
    // EmployeeAccount employeeAccount = new EmployeeAccount();
    // employeeAccount.setEmployeeId(1001);
    // employeeAccount.setEmployee(employee);
    // List<EmployeeAccount> employeeAccounts = new ArrayList<EmployeeAccount>();
    // employeeAccounts.add(employeeAccount);

    // List<Employee> actual = employeeAccountMapper.findAllByNameIsNull();
    // assertEquals(employeeAccounts, actual);
    // }

    // @Test
    // void findEmployeeNameByEmployeeIdTest_OK() {
    // Integer employeeId = 1001;
    // String actual =
    // employeeAccountMapper.findEmployeeNameByEmployeeId(employeeId);
    // assertEquals("フルネス太郎", actual);
    // }

    // @Test
    // void inputEmployeeAccountTest_OK() {

    // EmployeeAccount employeeAccount = new EmployeeAccount();
    // employeeAccount.setEmployeeId(1001);
    // employeeAccount.setName("fullness");
    // employeeAccount.setPassword(
    // "6a3c48514139efd14cefd3e221f71b37639e2451225cf56b1889e331d5ac48ef96db2e7a50e3284656ad56666f9e6e334a2f9cf364ebdff2114b3f40de8117e9");
    // int actual = employeeAccountMapper.inputEmployeeAccount(employeeAccount);
    // assertEquals(1, actual);
    // }

    @Test
    void selectByNameReturnAccount() {
        EmployeeAccount account = employeeAccountMapper.selectByName("fullness2");

        assertNotNull(account);
        assertAll(
                () -> assertEquals(102, account.getId()),
                () -> assertEquals(1002, account.getEmployeeId()),
                () -> assertEquals("fullness2", account.getName()),
                () -> assertEquals(
                        "f144784fe5a8f72f3bdfb9ca32f3b8e70816e4da40faa13e942460105fc35603cea2abce47573e580fe7c98d51e2a203c0b7071b967a196d701f694742456305",
                        account.getPassword()));
    }
}
