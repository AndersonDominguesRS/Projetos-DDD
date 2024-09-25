package com.example.petfriends_transporte.service;

import com.example.petfriends_transporte.model.Expedicao;
import com.example.petfriends_transporte.model.Transportadora;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface TransportadoraService {
    List<Transportadora> lista();
    Optional<Transportadora> transportadoraId(Integer id);
    void salva(Transportadora transportadora);
    void delete(Integer id);
    void atualiza(Integer id, Transportadora transportadora) throws JsonProcessingException;
}
