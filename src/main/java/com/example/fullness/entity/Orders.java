package com.example.fullness.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Orders implements Serializable{
    private Serial id;
    private Timestamp orderData;
    private Integer amountTotal;
    private Integer customerId;
    private Integer orderStatusId;
    private Integer paymentMethodId;

}