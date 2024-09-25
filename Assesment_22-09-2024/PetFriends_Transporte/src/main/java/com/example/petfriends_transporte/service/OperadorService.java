package com.example.petfriends_transporte.service;

import com.example.petfriends_transporte.model.Expedicao;
import com.example.petfriends_transporte.model.Operador;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface OperadorService {

    List<Operador> lista();
    Optional<Operador> operadorId(Integer id);
    void salva(Operador operador);
    void delete(Integer id);
    void atualiza(Integer id, Operador operador) throws JsonProcessingException;
}
