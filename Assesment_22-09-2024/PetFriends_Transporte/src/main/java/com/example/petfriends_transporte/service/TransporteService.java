package com.example.petfriends_transporte.service;

import com.example.petfriends_transporte.model.Transporte;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface TransporteService {
    List<Transporte> lista();
    Optional<Transporte> transportadoraId(Integer id);
    void salva(Transporte transportadora);
    void delete(Integer id);
    void atualiza(Integer id, Transporte transportadora) throws JsonProcessingException;
}
