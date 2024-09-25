package com.example.petfriends_transporte.infraMessage;


import com.example.petfriends_transporte.eventos.EstadoPedidoMudouTransportadora;
import com.example.petfriends_transporte.mesageDeserializer.TransporteDeserializer;
import com.example.petfriends_transporte.mesageSerializer.TransporteMudouSerializer;
import com.example.petfriends_transporte.model.Expedicao;
import com.example.petfriends_transporte.repository.ExpedicaoRepository;
import com.example.petfriends_transporte.repository.TransportadoraRepository;
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
public class PetFriendsTransporteMessageConfig {

    @Autowired
    private ExpedicaoRepository expedicaoRepository;
    
    @Bean
    public JacksonPubSubMessageConverter estadoMudouConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(EstadoPedidoMudouTransportadora.class, new TransporteMudouSerializer());
        simpleModule.addDeserializer(EstadoPedidoMudouTransportadora.class, new TransporteDeserializer());
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
                "topic-transportadora-sub");
        adapter.setOutputChannel(messageChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(EstadoPedidoMudouTransportadora.class);
        return adapter;
    }
    @ServiceActivator(inputChannel = "inputMessageChannel")
    public void messageReceiver(EstadoPedidoMudouTransportadora payload,
                                @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE)
                                ConvertedBasicAcknowledgeablePubsubMessage<EstadoPedidoMudouTransportadora> message) {
        Expedicao despacho = new Expedicao();
        despacho.setPedidoStatus(payload.getEstado());
        despacho.setIdPedido(payload.getIdPedido());
        expedicaoRepository.save(despacho);

        log.info("***** PEDIDO RECEBIDO NA EXPEDIÇÃO---> " + payload + "EXPEDIÇÃO----> " + despacho);
        message.ack();
    }
}















