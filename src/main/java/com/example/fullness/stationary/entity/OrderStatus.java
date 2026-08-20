package com.example.fullness.stationary.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderStatus implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id; // 注文ステータスID
    private String name; // 注文ステータス名

}
