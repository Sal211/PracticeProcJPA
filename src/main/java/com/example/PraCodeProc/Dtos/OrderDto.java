package com.example.PraCodeProc.Dtos;

import com.example.PraCodeProc.Entites.Customers;
import com.example.PraCodeProc.Entites.OrderDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderDto {
    Integer orderId =0;
    LocalDateTime orderDate;
    Customers customer;
    String orderStatus;
    LocalDateTime shippingDate;
    private List<OrderDetail> orderDetails;
}
