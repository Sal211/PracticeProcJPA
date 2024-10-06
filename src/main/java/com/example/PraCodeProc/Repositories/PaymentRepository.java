package com.example.PraCodeProc.Repositories;

import com.example.PraCodeProc.Entites.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
