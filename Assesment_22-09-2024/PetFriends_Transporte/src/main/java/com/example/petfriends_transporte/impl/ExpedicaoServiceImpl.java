package com.example.petfriends_transporte.impl;

import com.example.petfriends_transporte.exception.ResourseNotFoundException;
import com.example.petfriends_transporte.model.Expedicao;
import com.example.petfriends_transporte.repository.ExpedicaoRepository;
import com.example.petfriends_transporte.service.ExpedicaoService;
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
@RequiredArgsConstructor
@Slf4j
public class ExpedicaoServiceImpl implements ExpedicaoService {

    @Autowired
    private ExpedicaoRepository expedicaoRepository;

    @Autowired
    private  PubSubTemplate pubSubTemplate;

    @Autowired
    private JacksonPubSubMessageConverter converter;

    @Override
    public List<Expedicao> lista(){
        return expedicaoRepository.findAll();
    }

    @Override
    public Optional<Expedicao> expedicaoId(Integer id) {
        return Optional.empty();
    }

    @Override
    public void salva(Expedicao expedicao) {

//
//
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("topic-almoxarifado", expedicao);
        log.info("***** Mensagem Publicada ---> " + expedicao);


        expedicaoRepository.save(expedicao);
    }

    @Override
    public void delete(Integer id) {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente no delete");
        }
        expedicaoRepository.deleteById(id);
    }

    private boolean resourceNotFound(Integer id) {
        return expedicaoRepository.findAll().stream().filter(a -> a.getId() == id).findFirst().isEmpty();
    }

    @Override
    public void atualiza(Integer id, Expedicao expedicao) throws JsonProcessingException {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente na atualização");
        }
        expedicao.setId(id);
        expedicaoRepository.save(expedicao);

    }
}
