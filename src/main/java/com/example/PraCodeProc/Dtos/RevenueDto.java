package com.example.PraCodeProc.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RevenueDto {
    String dateType = "";
    LocalDateTime SpecificDate = null;
    Integer customerId = 0;
    Integer productId = 0;
    Integer categoryId = 0;
    String filterType = "";
}
