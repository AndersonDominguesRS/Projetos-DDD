package com.example.petfriends_almoxarifado.service;

import com.example.petfriends_almoxarifado.model.Produto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    List<Produto> lista();
    Optional<Produto> produtoId(Integer id);
    void salva(Produto produto);
    void delete(Integer id);
    void atualiza(Integer id, Produto produto) throws JsonProcessingException;

}
