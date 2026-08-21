package com.example.fullness.stationary.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Orders implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id; // 注文ID
    private Timestamp orderData; // 注文日
    private Integer amountTotal; // 合計金額
    private Integer customerId; // 顧客ID(外部キー)
    private Integer orderStatusId; // 注文ステータスID(外部キー)
    private Integer paymentMethodId; // 支払い方法ID(外部キー)

}