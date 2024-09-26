package com.example.petfriends_transporte.impl;

import com.example.petfriends_transporte.exception.ResourseNotFoundException;
import com.example.petfriends_transporte.model.Operador;
import com.example.petfriends_transporte.repository.OperadorRepository;
import com.example.petfriends_transporte.service.OperadorService;
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
public class OperadorServiceImpl  implements OperadorService {

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Autowired
    private JacksonPubSubMessageConverter converter;

    @Override
    public List<Operador> lista(){
        return operadorRepository.findAll();
    }

    @Override
    public Optional<Operador> operadorId(Integer id) {
        return operadorRepository.findById(id);
    }

    @Override
    public void salva(Operador operador) {

//
//
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("topic-almoxarifado", operador);
        log.info("***** Mensagem Publicada ---> " + operador);


        operadorRepository.save(operador);
    }

    @Override
    public void delete(Integer id) {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente no delete");
        }
        operadorRepository.deleteById(id);
    }

    private boolean resourceNotFound(Integer id) {
        return operadorRepository.findAll().stream().filter(a -> a.getId() == id).findFirst().isEmpty();
    }

    @Override
    public void atualiza(Integer id, Operador operador) throws JsonProcessingException {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente na atualização");
        }
        operador.setId(id);
        operadorRepository.save(operador);

    }
}
