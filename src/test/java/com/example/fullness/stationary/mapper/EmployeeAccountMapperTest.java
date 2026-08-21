package com.example.fullness.stationary.mapper;

import com.example.fullness.stationary.entity.EmployeeAccount;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Sql({
        "/sql/employee_account_schema.sql",
        "/sql/employee_account_data.sql"
})
class EmployeeAccountMapperTest {

    @Autowired
    private EmployeeAccountMapper employeeAccountMapper;

    @Test
    void selectByName_returnAccount() {
        EmployeeAccount account = employeeAccountMapper.selectByName("fullness2");

        assertThat(account).isNotNull();
        assertThat(account.getId()).isEqualTo(100);
        assertThat(account.getEmployeeId()).isEqualTo(1002);
        assertThat(account.getName()).isEqualTo("fullness2");
        assertThat(account.getPassword()).isEqualTo("f144784fe5a8f72f3bdfb9ca32f3b8e70816e4da40faa13e942460105fc35603cea2abce47573e580fe7c98d51e2a203c0b7071b967a196d701f694742456305");
    }

    
}