package com.example.PraCodeProc.Dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class CustomerRevenueDto {
    Integer customerId;
    String firstName;
    String lastName;
    BigDecimal revenue;

    public CustomerRevenueDto(Integer customerId,String firstName,String lastName,BigDecimal revenue){
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.revenue = revenue;
    }

}
