package com.example.tp2_ddd_15_09_2024.service;

import com.example.tp2_ddd_15_09_2024.model.Produto;
import com.example.tp2_ddd_15_09_2024.repository.ProdutoRepository;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProdutoService {

    @Autowired
    private PubSubTemplate pubSubTemplate;
    @Autowired
    private JacksonPubSubMessageConverter converter;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Integer id) {
        return produtoRepository.findById(id);
    }

    public Produto save(Produto produto) {

        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("teste", produto);
        log.info("***** NOVO PRODUTO PUBLICADO---> " + produto);

        return produtoRepository.save(produto);

    }
}
