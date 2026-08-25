package com.example.fullness.stationary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.mapper.EmployeeAccountMapper;
import com.example.fullness.stationary.service.EmployeeAccountService;

@Service
@Transactional(readOnly = false)
public class EmployeeAccountServiceImpl implements EmployeeAccountService {

    @Autowired
    EmployeeAccountMapper employeeAccountMapper;

    @Override
    public boolean canRegisterAccountName(String accountName) {

        // バリデーションをチェックして論理型を返す
        // 重複しているときはfalseを返す
        boolean existBooleanCheck = employeeAccountMapper.existsByAccountName(accountName);
        System.out.println(existBooleanCheck);
        boolean returnBoolean = false;
        if (existBooleanCheck == false) {
            return true;
        }
        return returnBoolean;
    }

    @Override
    public List<Employee> showAllByNameIsNull() {
        return employeeAccountMapper.findAllByNameIsNull();
    }

    @Override
    public String showEmployeeNameByEmployeeId(Integer employeeId) {
        return employeeAccountMapper.findEmployeeNameByEmployeeId(employeeId);
    }

    @Override
    public boolean registerEmployeeAccount(EmployeeAccount employeeAccount) {
        if (employeeAccountMapper.inputEmployeeAccount(employeeAccount) == 1) {
            return true;
        } else {
            return false;
        }
    }

}
