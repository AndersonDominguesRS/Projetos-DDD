package com.example.tp2_ddd_15_09_2024.service;

import com.example.tp2_ddd_15_09_2024.model.Estoque;
import com.example.tp2_ddd_15_09_2024.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    private EstoqueRepository estoqueRepository;

    public List<Estoque> getAllEstoque() {
        return estoqueRepository.findAll();
    }

    public void insertEstoque(Estoque estoque) {
        estoqueRepository.save(estoque);
    }

}
