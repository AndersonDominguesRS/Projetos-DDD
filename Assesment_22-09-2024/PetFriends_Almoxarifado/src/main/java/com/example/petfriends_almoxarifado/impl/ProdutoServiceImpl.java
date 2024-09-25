package com.example.petfriends_almoxarifado.impl;


import com.example.petfriends_almoxarifado.exception.ResourseNotFoundException;
import com.example.petfriends_almoxarifado.model.Produto;
import com.example.petfriends_almoxarifado.repository.ProdutoRepository;
import com.example.petfriends_almoxarifado.service.ProdutoService;
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
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Autowired
    private JacksonPubSubMessageConverter converter;

    @Autowired
    private final ProdutoRepository produtoRepository;

    @Override
    public List<Produto> lista() {
        return produtoRepository.findAll();
    }

    @Override
    public Optional<Produto> produtoId(Integer id) {
        return Optional.empty();
    }

    @Override
    public void salva(Produto produto) {

        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("topic-almoxarifado", produto);
        log.info("***** Mensagem Publicada ---> " + produto);


        produtoRepository.save(produto);
    }

    @Override
    public void delete(Integer id) {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente no delete");
        }
        produtoRepository.deleteById(id);
    }

    private boolean resourceNotFound(Integer id) {
        return produtoRepository.findAll().stream().filter(a -> a.getId() == id).findFirst().isEmpty();
    }

    @Override
    public void atualiza(Integer id, Produto produto) throws JsonProcessingException {
        if(resourceNotFound(id)){
            throw new ResourseNotFoundException("ID inexistente na atualização");
        }
        produto.setId(id);
        produtoRepository.save(produto);

    }
}
