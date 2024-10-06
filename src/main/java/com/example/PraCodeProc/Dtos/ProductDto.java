package com.example.PraCodeProc.Dtos;

import com.example.PraCodeProc.Entites.Category;
import com.example.PraCodeProc.Entites.OrderDetail;
import com.example.PraCodeProc.Entites.Reviews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductDto {
    Integer ProductID =0;
    String productName;
    String description;
    BigDecimal price;
    Integer stockQuantity;
    Category category;
    LocalDateTime createdDate;
    private List<Reviews> reviews;
    private List<OrderDetail> orderDetails;
}
