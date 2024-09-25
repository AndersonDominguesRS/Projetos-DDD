package com.example.petfriends_almoxarifado.service;


import com.example.petfriends_almoxarifado.exception.ResourseNotFoundException;
import com.example.petfriends_almoxarifado.model.Estoque;
import com.example.petfriends_almoxarifado.repository.EstoqueRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    private  EstoqueRepository estoqueRepository;


    public List<Estoque> lista() {
        return estoqueRepository.findAll();
    }


    public Optional<Estoque> aluinoId(Integer id) {
        if (id < 0) {
            throw new ResourseNotFoundException("ID inexistente");
        } else {
            Optional<Estoque> estoqueOpt = estoqueRepository.findAll().stream().filter(a -> a.getId() == id).findFirst();

            if (estoqueOpt.isEmpty()) throw new ResourseNotFoundException("Estoque nao encontrado");

            return estoqueRepository.findById(id);
        }
    }


    public void salva(Estoque estoque) {
        estoqueRepository.save(estoque);
    }

    public void delete(Integer id) {

        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente no delete");
        }
        estoqueRepository.deleteById(id);
    }

    private boolean resourceNotFound(Integer id) {
        return estoqueRepository.findAll().stream().filter(a -> a.getId() == id).findFirst().isEmpty();
    }


    public void atualiza(Integer id, Estoque estoque) throws JsonProcessingException {

        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente na atualização");
        }

        estoque.setId(id);
        estoqueRepository.save(estoque);
    }

}
