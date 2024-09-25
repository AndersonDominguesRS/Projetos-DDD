package com.example.petfriends_almoxarifado.messageService;

import com.example.petfriends_almoxarifado.eventos.EstadoPedidoMudouAlmox;
import com.example.petfriends_almoxarifado.mesageSerializer.AlmoxSerializer;
import com.example.petfriends_almoxarifado.messageDeserializer.AlmoxDeserializer;
import com.example.petfriends_almoxarifado.model.Separacao;
import com.example.petfriends_almoxarifado.repository.SeparacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.google.cloud.spring.pubsub.support.converter.ConvertedBasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
@Slf4j
public class PetFriendsAlmoxMessageConfig {

    @Autowired
    private SeparacaoRepository separacaoRepository;
    
    @Bean
    public JacksonPubSubMessageConverter estadoMudouConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(EstadoPedidoMudouAlmox.class, new AlmoxSerializer());
        simpleModule.addDeserializer(EstadoPedidoMudouAlmox.class, new AlmoxDeserializer());
        objectMapper.registerModule(simpleModule);
        return new JacksonPubSubMessageConverter(objectMapper);
    }

    @Bean
    public MessageChannel inputMessageChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter inboundChannelAdapter(
            @Qualifier("inputMessageChannel") MessageChannel messageChannel, PubSubTemplate pubSubTemplate) {

        pubSubTemplate.setMessageConverter(estadoMudouConverter());
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate,
                "topic-almoxarifado-sub");
        adapter.setOutputChannel(messageChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(EstadoPedidoMudouAlmox.class);
        return adapter;
    }
    @ServiceActivator(inputChannel = "inputMessageChannel")
    public void messageReceiver(EstadoPedidoMudouAlmox payload,
                                @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE)
                                ConvertedBasicAcknowledgeablePubsubMessage<EstadoPedidoMudouAlmox> message) {
        Separacao separacao = new Separacao(payload.getIdPedido(), payload.getEstado());
        separacaoRepository.save(separacao);

        log.info("***** PEDIDO RECEBIDO NA EXPEDIÇÃO---> " + payload + "SEPARACAO----> " + separacao);
        message.ack();
    }
}















