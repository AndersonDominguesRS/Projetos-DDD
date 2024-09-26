package com.example.petfriends_almoxarifado.service;


import com.example.petfriends_almoxarifado.model.Estoque;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;


public interface EstoqueService {

    List<Estoque> lista();
    Optional<Estoque> estoqueId(Integer id);
    void salva(Estoque estoque);
    void delete(Integer id);
    void atualiza(Integer id, Estoque estoque) throws JsonProcessingException;


}
