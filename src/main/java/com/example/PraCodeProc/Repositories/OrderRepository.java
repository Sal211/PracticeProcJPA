package com.example.PraCodeProc.Repositories;

import com.example.PraCodeProc.Entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
