package com.example.petfriends_almoxarifado.controller;


import com.example.petfriends_almoxarifado.model.Estoque;
import com.example.petfriends_almoxarifado.service.EstoqueService;
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
        return estoqueService.lista();
    }
}
