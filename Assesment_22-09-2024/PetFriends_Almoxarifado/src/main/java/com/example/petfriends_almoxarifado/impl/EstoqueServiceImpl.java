package com.example.petfriends_almoxarifado.impl;

import com.example.petfriends_almoxarifado.exception.ResourseNotFoundException;
import com.example.petfriends_almoxarifado.model.Estoque;
import com.example.petfriends_almoxarifado.repository.EstoqueRepository;
import com.example.petfriends_almoxarifado.service.EstoqueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstoqueServiceImpl implements EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Autowired
    private JacksonPubSubMessageConverter converter;

    @Override
    public List<Estoque> lista(){
        return estoqueRepository.findAll();
    }

    @Override
    public Optional<Estoque> estoqueId(Integer id) {

        return estoqueRepository.findById(id);
    }

    @Override
    public void salva(Estoque estoque) {

//
//
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("topic-almoxarifado", estoque);
        log.info("***** Mensagem Publicada ---> " + estoque);


        estoqueRepository.save(estoque);
    }

    @Override
    public void delete(Integer id) {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente no delete");
        }
        estoqueRepository.deleteById(id);
    }

    private boolean resourceNotFound(Integer id) {
        return estoqueRepository.findAll().stream().filter(a -> a.getId() == id).findFirst().isEmpty();
    }

    @Override
    public void atualiza(Integer id, Estoque estoque) throws JsonProcessingException {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente na atualização");
        }
        estoque.setId(id);
        estoqueRepository.save(estoque);

    }
}
