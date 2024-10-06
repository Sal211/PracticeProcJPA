package com.example.PraCodeProc.Dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetRevenueResponse<T> {
    T Data;
    BigDecimal totalRevenue;
}
