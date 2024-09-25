package com.example.petfriends_almoxarifado.controller;


import com.example.petfriends_almoxarifado.model.Produto;
import com.example.petfriends_almoxarifado.service.ProdutoService;
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
        return produtoService.lista();
    }

    @PostMapping
    public void add(@RequestBody Produto produto) {
         produtoService.salva(produto);
    }
}
