package com.example.PraCodeProc.Dtos;

import com.example.PraCodeProc.Entites.Customers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomerRevenueResponse {
    BigDecimal totalRevenue = BigDecimal.ZERO;
    String FullName = "";
    Float percentageOfTotal = 0F;
    Integer customerId;
}
