package com.example.PraCodeProc.Repositories;

import com.example.PraCodeProc.Entites.Customers;
import com.example.PraCodeProc.Entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Optional<Product> findById(Integer productId);
}
