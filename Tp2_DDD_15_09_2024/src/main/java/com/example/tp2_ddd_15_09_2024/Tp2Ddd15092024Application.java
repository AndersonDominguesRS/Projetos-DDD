package com.example.tp2_ddd_15_09_2024;

import com.example.tp2_ddd_15_09_2024.model.Produto;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Tp2Ddd15092024Application implements CommandLineRunner {

    @Autowired
    private PubSubTemplate pubSubTemplat;
    @Autowired
    private JacksonPubSubMessageConverter converter;


    public static void main(String[] args) {
        SpringApplication.run(Tp2Ddd15092024Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        String mensagem = "TESTE NA INICIALIZAÇÃO";
//        pubSubTemplat.publish("teste", mensagem);
//        log.info("***** Mensagem Publicada ---> " + mensagem);
        Produto produto = new Produto();
        produto.setNome("Produto 1");
        produto.setEstado("novo");
        produto.setCodigo(1234);
        produto.setValor(100);

        pubSubTemplat.setMessageConverter(converter);
        pubSubTemplat.publish("teste", produto);
        log.info("***** Mensagem Publicada ---> " + produto);
    }
}
