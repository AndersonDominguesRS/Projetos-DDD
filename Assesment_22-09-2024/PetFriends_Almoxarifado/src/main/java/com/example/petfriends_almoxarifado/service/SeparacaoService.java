package com.example.petfriends_almoxarifado.service;

import com.example.petfriends_almoxarifado.model.Separacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SeparacaoService {

    List<Separacao> lista();
    Optional<Separacao> separacaoId(Integer id);
    void salva(Separacao separacao);
    void delete(Integer id);
    void atualiza(Integer id, Separacao separacao) throws JsonProcessingException;
}
