package com.example.petfriends_almoxarifado;

import com.example.petfriends_almoxarifado.enumerate.PedidoStatus;
import com.example.petfriends_almoxarifado.eventos.EstadoPedidoMudouAlmox;
import com.example.petfriends_almoxarifado.model.Pedido;
import com.example.petfriends_almoxarifado.model.Separacao;
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
public class PetFriendsAlmoxarifadoApplication  implements CommandLineRunner {

    @Autowired
    private PubSubTemplate pubSubTemplat;
    @Autowired
    private JacksonPubSubMessageConverter converter;

    public static void main(String[] args) {
        SpringApplication.run(PetFriendsAlmoxarifadoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        EstadoPedidoMudouAlmox estadoPedidoMudouAlmox = new EstadoPedidoMudouAlmox();
        Date date= new Date();
        estadoPedidoMudouAlmox.setEstado(PedidoStatus.NOVO);
        estadoPedidoMudouAlmox.setIdPedido(1);
        estadoPedidoMudouAlmox.setMomento(date);

        pubSubTemplat.setMessageConverter(converter);
        pubSubTemplat.publish("topic-almoxarifado", estadoPedidoMudouAlmox);
        log.info("***** MESNSAGEM PUBLICADA NA INICIALIZAÇÃO ---> " + estadoPedidoMudouAlmox);
    }
}
