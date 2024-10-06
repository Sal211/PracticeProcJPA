package com.example.PraCodeProc.Dtos;

import com.example.PraCodeProc.Entites.OrderDetail;
import com.example.PraCodeProc.Entites.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class PaymentDto {
    Integer paymentId =0;
    LocalDateTime paymentDate;
    BigDecimal amount;
    PaymentMethod paymentMethod;
    String paymentStatus;
    private List<OrderDetail> orderDetails;
}
