package com.example.fullness.stationary.service;

import org.springframework.stereotype.Service;

@Service
public interface LoginFailureService {

    // 失敗回数をカウントアップ
    public void incrementFailureCount(String accountName);

    // 失敗回数をリセット
    public void resetFailureCount(String accountName);

    // 現在の失敗回数を取得
    public int getFailureCount(String accountName);

    // ロックされているか判定
    public boolean isLocked(String accountName) ;
}
