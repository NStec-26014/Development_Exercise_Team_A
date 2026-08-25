package com.example.fullness.stationary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.mapper.EmployeeAccountMapper;
import com.example.fullness.stationary.service.impl.EmployeeAccountServiceImpl;

@SpringBootTest
public class EmployeeAccountServiceTest {

    @Mock
    private EmployeeAccountMapper employeeAccountMapper;

    @InjectMocks
    private EmployeeAccountServiceImpl employeeAccountServiceImpl;

    @Test
    void canRegisterAccountNameTest_OK() {

        when(employeeAccountMapper.existsByAccountName("fullness2")).thenReturn(true);
        String accountName = "fullness2";
        // 重複している場合は件数1件がDBから返され、actualは登録できないことを意味するfalseになる
        // 重複していない場合はactualは登録できることを意味するtrueになる
        boolean actual = employeeAccountServiceImpl.canRegisterAccountName(accountName);

        verify(employeeAccountMapper).existsByAccountName(accountName);
        assertEquals(false, actual);

    }

    // @Test
    // void showAllByNameIsNullTest_OK() {

    // Employee employee = new Employee();
    // employee.setId(1001);
    // employee.setName("フルネス太郎");
    // EmployeeAccount employeeAccount = new EmployeeAccount();
    // employeeAccount.setEmployeeId(1001);
    // employeeAccount.setEmployee(employee);
    // List<EmployeeAccount> employeeAccounts = new ArrayList<EmployeeAccount>();
    // employeeAccounts.add(employeeAccount);
    // List<Employee> actual = employeeAccountServiceImpl.showAllByNameIsNull();
    // verify(employeeAccountMapper).findAllByNameIsNull();
    // assertEquals(employeeAccounts, actual);

    // }

    // @Test
    // void showEmployeeNameByEmployeeIdTest_OK() {

    // Integer employeeId = 1001;
    // String actual =
    // employeeAccountServiceImpl.showEmployeeNameByEmployeeId(employeeId);
    // verify(employeeAccountMapper).findEmployeeNameByEmployeeId(employeeId);
    // assertEquals("フルネス太郎", actual);
    // }

    // @Test
    // void registerEmployeeAccountTest_OK() {

    // EmployeeAccount employeeAccount = new EmployeeAccount();
    // employeeAccount.setEmployeeId(1001);
    // employeeAccount.setName("fullness");
    // employeeAccount.setPassword(
    // "6a3c48514139efd14cefd3e221f71b37639e2451225cf56b1889e331d5ac48ef96db2e7a50e3284656ad56666f9e6e334a2f9cf364ebdff2114b3f40de8117e9");
    // boolean actual =
    // employeeAccountServiceImpl.registerEmployeeAccount(employeeAccount);
    // verify(employeeAccountMapper).inputEmployeeAccount(employeeAccount);
    // assertEquals(true, actual);
    // }
}
