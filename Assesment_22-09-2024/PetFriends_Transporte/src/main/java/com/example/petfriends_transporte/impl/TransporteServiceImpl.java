package com.example.petfriends_transporte.impl;

import com.example.petfriends_transporte.exception.ResourseNotFoundException;
import com.example.petfriends_transporte.model.Transporte;
import com.example.petfriends_transporte.repository.TransporteRepository;
import com.example.petfriends_transporte.service.TransporteService;
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
public class TransporteServiceImpl implements TransporteService {

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Autowired
    private JacksonPubSubMessageConverter converter;

    @Override
    public List<Transporte> lista(){
        return transporteRepository.findAll();
    }

    @Override
    public Optional<Transporte> transportadoraId(Integer id) {
        return transporteRepository.findById(id);
    }

    @Override
    public void salva(Transporte transportadora) {

//
//
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("topic-almoxarifado", transportadora);
        log.info("***** Mensagem Publicada ---> " + transportadora);


        transporteRepository.save(transportadora);
    }

    @Override
    public void delete(Integer id) {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente no delete");
        }
        transporteRepository.deleteById(id);
    }

    private boolean resourceNotFound(Integer id) {
        return transporteRepository.findAll().stream().filter(a -> a.getId() == id).findFirst().isEmpty();
    }

    @Override
    public void atualiza(Integer id, Transporte transportadora) throws JsonProcessingException {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente na atualização");
        }
        transportadora.setId(id);
        transporteRepository.save(transportadora);

    }
}
