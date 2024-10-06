package com.example.PraCodeProc.Dtos;

import lombok.Data;

@Data
public class OrderProductDto {
    Integer productId;
    Integer customerId;
    Integer quantity;
    Float discount = 0F;
    Integer paymentMethodId = 6;
}
