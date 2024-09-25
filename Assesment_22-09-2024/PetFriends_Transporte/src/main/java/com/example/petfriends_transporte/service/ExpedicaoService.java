package com.example.petfriends_transporte.service;

import com.example.petfriends_transporte.model.Expedicao;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface ExpedicaoService {
    List<Expedicao> lista();
    Optional<Expedicao> expedicaoId(Integer id);
    void salva(Expedicao expedicao);
    void delete(Integer id);
    void atualiza(Integer id, Expedicao expedicao) throws JsonProcessingException;
}
