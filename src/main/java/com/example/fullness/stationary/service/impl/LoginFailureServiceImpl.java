package com.example.fullness.stationary.service.impl;

import com.example.fullness.stationary.service.LoginFailureService;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginFailureServiceImpl implements LoginFailureService {
    private final ConcurrentHashMap<String, Integer> failureMap = new ConcurrentHashMap<>();
    private static final int MAX_FAILURE_COUNT = 5;

    @Override
    public void incrementFailureCount(String accountName) {
        failureMap.merge(accountName, 1, Integer::sum);
    }

    @Override
    public void resetFailureCount(String accountName) {
        failureMap.remove(accountName);
    }

    @Override
    public boolean isLocked(String accountName) {
        return failureMap.getOrDefault(accountName, 0) >= MAX_FAILURE_COUNT;
    }
    @Override
    public int getFailureCount(String accountName) {
        return failureMap.getOrDefault(accountName, 0);
    }

}