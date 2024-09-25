package com.example.petfriends_transporte.impl;

import com.example.petfriends_transporte.exception.ResourseNotFoundException;
import com.example.petfriends_transporte.model.Transportadora;
import com.example.petfriends_transporte.repository.TransportadoraRepository;
import com.example.petfriends_transporte.service.TransportadoraService;
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
public class TransportadoraServiceImpl implements TransportadoraService {

    @Autowired
    private TransportadoraRepository transportadoraRepository;

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Autowired
    private JacksonPubSubMessageConverter converter;

    @Override
    public List<Transportadora> lista(){
        return transportadoraRepository.findAll();
    }

    @Override
    public Optional<Transportadora> transportadoraId(Integer id) {
        return Optional.empty();
    }

    @Override
    public void salva(Transportadora transportadora) {

//
//
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("topic-almoxarifado", transportadora);
        log.info("***** Mensagem Publicada ---> " + transportadora);


        transportadoraRepository.save(transportadora);
    }

    @Override
    public void delete(Integer id) {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente no delete");
        }
        transportadoraRepository.deleteById(id);
    }

    private boolean resourceNotFound(Integer id) {
        return transportadoraRepository.findAll().stream().filter(a -> a.getId() == id).findFirst().isEmpty();
    }

    @Override
    public void atualiza(Integer id, Transportadora transportadora) throws JsonProcessingException {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente na atualização");
        }
        transportadora.setId(id);
        transportadoraRepository.save(transportadora);

    }
}
