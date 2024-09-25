package com.example.petfriends_almoxarifado.impl;

import com.example.petfriends_almoxarifado.exception.ResourseNotFoundException;
import com.example.petfriends_almoxarifado.model.Separacao;
import com.example.petfriends_almoxarifado.repository.SeparacaoRepository;
import com.example.petfriends_almoxarifado.service.SeparacaoService;
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
public class SeparcaoServiceImpl implements SeparacaoService {


    @Autowired
    private  SeparacaoRepository separacaoRepository;

    @Autowired
    private  PubSubTemplate pubSubTemplate;

    @Autowired
    private JacksonPubSubMessageConverter converter;

    @Override
    public List<Separacao> lista(){
        return separacaoRepository.findAll();
    }

    @Override
    public Optional<Separacao> separacaoId(Integer id) {
        return Optional.empty();
    }

    @Override
    public void salva(Separacao separacao) {

//
//
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("topic-almoxarifado", separacao);
        log.info("***** Mensagem Publicada ---> " + separacao);


        separacaoRepository.save(separacao);
    }

    @Override
    public void delete(Integer id) {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente no delete");
        }
        separacaoRepository.deleteById(id);
    }

    private boolean resourceNotFound(Integer id) {
        return separacaoRepository.findAll().stream().filter(a -> a.getId() == id).findFirst().isEmpty();
    }

    @Override
    public void atualiza(Integer id, Separacao separacao) throws JsonProcessingException {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente na atualização");
        }
        separacao.setId(id);
        separacaoRepository.save(separacao);

    }
}
