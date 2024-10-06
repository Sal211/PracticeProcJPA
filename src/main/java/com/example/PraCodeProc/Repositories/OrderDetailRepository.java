package com.example.PraCodeProc.Repositories;

import com.example.PraCodeProc.Entites.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {

    @Query(value = "SELECT SUM(od.totalPrice) FROM TblOrderDetail od INNER JOIN TblOrders o ON  od.OrderId = o.OrderId" +
            "  WHERE o.orderDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    BigDecimal findTotalRevenueByOrderDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
