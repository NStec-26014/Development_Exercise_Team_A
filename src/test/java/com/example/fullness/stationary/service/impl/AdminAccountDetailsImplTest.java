package com.example.fullness.stationary.service.impl;

import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.mapper.EmployeeAccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminAccountDetailsImplTest {

    @Mock
    private EmployeeAccountMapper employeeAccountMapper;

    @InjectMocks
    private AdminAccountDetailsImpl adminAccountDetailsImpl;

    @Test
    void CallEmployeeAccountMapper() {
        String username = "fullness2";
        EmployeeAccount account = new EmployeeAccount();
        account.setId(100);
        account.setEmployeeId(1002);
        account.setName(username);
        account.setPassword("hashed-password");

        when(employeeAccountMapper.selectByName(username)).thenReturn(account);
        adminAccountDetailsImpl.loadUserByUsername(username);
        verify(employeeAccountMapper, times(1)).selectByName(username);
    }

    @Test
    void loadUserByUsernameReturnUserDetails() {
        Integer id = 100;
        Integer employeeId = 1002;
        String username = "fullness2";
        String password = "f144784fe5a8f72f3bdfb9ca32f3b8e70816e4da40faa13e942460105fc35603cea2abce47573e580fe7c98d51e2a203c0b7071b967a196d701f694742456305";

        EmployeeAccount account = new EmployeeAccount();
        account.setId(id);
        account.setEmployeeId(employeeId);
        account.setName(username);
        account.setPassword(password);

        when(employeeAccountMapper.selectByName(username)).thenReturn(account);

        UserDetails result = adminAccountDetailsImpl.loadUserByUsername(username);

        assertEquals(id, account.getId());
        assertEquals(employeeId, account.getEmployeeId());
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());

        verify(employeeAccountMapper, times(1)).selectByName(username);
    }

    

}