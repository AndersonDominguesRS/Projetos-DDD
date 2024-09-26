package com.example.petfriends_transporte;

import com.example.petfriends_transporte.enumerate.PedidoStatus;
import com.example.petfriends_transporte.eventos.EstadoPedidoMudouTransportadora;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
@Slf4j
public class PetFriendsTransporteApplication implements CommandLineRunner {

    @Autowired
    private PubSubTemplate pubSubTemplat;
    @Autowired
    private JacksonPubSubMessageConverter converter;

    public static void main(String[] args) {
        SpringApplication.run(PetFriendsTransporteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        EstadoPedidoMudouTransportadora estadoPedidoMudouTransportadora = new EstadoPedidoMudouTransportadora();
        Date date= new Date();
        estadoPedidoMudouTransportadora.setEstado(PedidoStatus.NOVO);
        estadoPedidoMudouTransportadora.setIdPedido(1);
        estadoPedidoMudouTransportadora.setMomento(date);

        pubSubTemplat.setMessageConverter(converter);
        pubSubTemplat.publish("topic-transportadora", estadoPedidoMudouTransportadora);
        log.info("***** MESNSAGEM PUBLICADA NA INICIALIZAÇÃO ---> " + estadoPedidoMudouTransportadora);

    }
}
