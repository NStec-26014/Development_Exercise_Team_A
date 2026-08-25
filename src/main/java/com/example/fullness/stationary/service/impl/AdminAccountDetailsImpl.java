package com.example.fullness.stationary.service.impl;

import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.mapper.EmployeeAccountMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminAccountDetailsImpl implements UserDetailsService{


    @Autowired
	private EmployeeAccountMapper employeeAccountMapper;
	public AdminAccountDetailsImpl(EmployeeAccountMapper employeeAccountMapper) {
		this.employeeAccountMapper = employeeAccountMapper;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EmployeeAccount account = employeeAccountMapper.selectByName(username);
		if (account == null) {
			throw new UsernameNotFoundException("アカウント名またはパスワードが正しくありません");
		}
		return User.builder()
				.username(account.getName())
				.password(account.getPassword())
				.build();
	}
}