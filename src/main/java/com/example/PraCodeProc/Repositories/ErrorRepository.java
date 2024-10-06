package com.example.PraCodeProc.Repositories;

import com.example.PraCodeProc.Entites.Errors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ErrorRepository extends JpaRepository<Errors,Integer> {

}
