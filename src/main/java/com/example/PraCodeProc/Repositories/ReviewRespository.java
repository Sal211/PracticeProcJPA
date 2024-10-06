package com.example.PraCodeProc.Repositories;

import com.example.PraCodeProc.Entites.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRespository extends JpaRepository<Reviews,Integer> {
}
