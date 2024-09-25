package com.example.petfriends_almoxarifado.repository;

import com.example.petfriends_almoxarifado.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
