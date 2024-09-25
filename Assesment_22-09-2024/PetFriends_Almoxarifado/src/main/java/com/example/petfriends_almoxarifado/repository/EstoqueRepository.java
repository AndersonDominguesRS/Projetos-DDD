package com.example.petfriends_almoxarifado.repository;


import com.example.petfriends_almoxarifado.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
}
