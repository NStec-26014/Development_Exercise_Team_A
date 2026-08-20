package com.example.fullness.stationary.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Customer implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id; // 顧客ID
    private String name; // 顧客名
    private String nameKana; // 顧客名（カタカナ）
    private String address1; // 住所1
    private String address2; // 住所2
    private String phoneNumber; // 電話番号
    private String mailAddress; // メールアドレス
    private String username; // アカウント名
    private String password; // パスワード(ハッシュ値)
    private Timestamp createdAt; // 登録日

}