package com.example.PraCodeProc.Dtos;

import com.example.PraCodeProc.Entites.Order;
import com.example.PraCodeProc.Entites.Payment;
import com.example.PraCodeProc.Entites.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderDetailDto {
    Integer orderDetailId =0;
    Order order;
    Product product;
    Payment payment;
    Integer quantity;
    BigDecimal unitPrice;
    float discount;
    BigDecimal totalPrice;
}
