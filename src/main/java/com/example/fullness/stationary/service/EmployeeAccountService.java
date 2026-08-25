package com.example.fullness.stationary.service;

import java.util.List;

import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;

public interface EmployeeAccountService {
    // アカウント名が重複していないかを確認
    boolean canRegisterAccountName(String accountName);

    // アカウントを作っていない社員のIDを検索
    List<Employee> showAllByNameIsNull();

    // 社員IDから社員名を取得
    String showEmployeeNameByEmployeeId(Integer employeeId);

    // アカウントを新規登録する
    boolean registerEmployeeAccount(EmployeeAccount employeeAccount);

}
