package com.example.tp2_ddd_15_09_2024.controller;

import com.example.tp2_ddd_15_09_2024.model.Estoque;
import com.example.tp2_ddd_15_09_2024.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public List<Estoque> getAllEstoque() {
        return estoqueService.getAllEstoque();
    }
}
