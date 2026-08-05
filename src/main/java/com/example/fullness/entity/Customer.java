package com.example.fullness.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Customer implements Serializable{
    private Serial id;
    private String name;
    private String nameKana;
    private String address1;
    private String address2;
    private String phoneNumber;
    private String mailAddress;
    private String username;
    private String password;
    private Timestamp createdAt;

}