package com.example.fullness.entity;

import lombok.Data;

@Data
public class Orders{
    private Serial id;
    private Timestamp orderData;
    private Integer amountTotal;
    private Integer customerId;
    private Integer orderStatusId;
    private Integer paymentMethodId;

}
