package com.example.tp2_ddd_15_09_2024.controller;

import com.example.tp2_ddd_15_09_2024.model.Produto;
import com.example.tp2_ddd_15_09_2024.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> getAll() {
        return produtoService.findAll();
    }

    @PostMapping
    public Produto add(@RequestBody Produto produto) {
        return produtoService.save(produto);
    }
}
