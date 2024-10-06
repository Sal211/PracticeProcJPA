package com.example.PraCodeProc.Repositories;

import com.example.PraCodeProc.Entites.Payment;
import com.example.PraCodeProc.Entites.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Integer> {
}
