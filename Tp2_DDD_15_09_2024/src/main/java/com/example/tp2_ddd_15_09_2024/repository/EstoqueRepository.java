package com.example.tp2_ddd_15_09_2024.repository;

import com.example.tp2_ddd_15_09_2024.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
}
