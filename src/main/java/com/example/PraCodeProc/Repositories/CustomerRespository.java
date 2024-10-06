package com.example.PraCodeProc.Repositories;

import com.example.PraCodeProc.Dtos.CustomerRevenueDto;
import com.example.PraCodeProc.Entites.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CustomerRespository extends JpaRepository<Customers,Integer> {

    Optional<Customers> findById(Integer customerId);

    @Query(value = "SELECT SUM(od.totalPrice) FROM TblOrderDetail od INNER JOIN TblOrders o ON  od.OrderId = o.OrderId " +
            "INNER JOIN TblCustomers c ON  c.CustomerId = o.CustomerId  WHERE o.orderDate BETWEEN :startDate AND :endDate And c.CustomerId = :customerId"
            , nativeQuery = true)
    BigDecimal findTotalRevenueByOrderDateBetweenById(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate , @Param("customerId") Integer customerId);

    @Query(value = "SELECT  c.CustomerID,c.FirstName,c.LastName,SUM(od.totalPrice) FROM TblOrderDetail od INNER JOIN TblOrders o ON  od.OrderId = o.OrderId " +
            "INNER JOIN TblCustomers c ON  c.CustomerId = o.CustomerId  WHERE o.orderDate BETWEEN :startDate AND :endDate GROUP BY c.CustomerID,c.FirstName,c.LastName "
            , nativeQuery = true)
    List<Object[]> findAllByOrderDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate );


}
