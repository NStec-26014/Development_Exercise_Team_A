package com.example.fullness.stationary.mapper;

import com.example.fullness.stationary.entity.Employee;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@Sql({
        "/sql/employee_schema.sql",
        "/sql/employee_data.sql"
})
class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    void selectById_returnEmployee() {
        Employee employee = employeeMapper.selectById(1002);

        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1002);
        assertThat(employee.getDepartmentId()).isEqualTo(2);
        assertThat(employee.getName()).isEqualTo("山田太郎");
        assertThat(employee.getName_kana()).isEqualTo("ヤマダタロウ");
    }
}
