package com.example.petfriends_transporte.mesageDeserializer;

import com.example.petfriends_transporte.enumerate.PedidoStatus;
import com.example.petfriends_transporte.eventos.EstadoPedidoMudouTransportadora;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TransporteDeserializer extends StdDeserializer<EstadoPedidoMudouTransportadora> {
    
    public TransporteDeserializer() {
        super(EstadoPedidoMudouTransportadora.class);
    }

    @Override
    public EstadoPedidoMudouTransportadora deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
        EstadoPedidoMudouTransportadora evento = null;
        JsonNode node = jp.getCodec().readTree(jp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        try {
            evento = new EstadoPedidoMudouTransportadora(
                    node.get("idPedido").asInt(),
                    PedidoStatus.valueOf(node.get("estado").asText()),
                    sdf.parse(node.get("momento").asText())
            );
        } catch (ParseException e) {
            throw new IOException("Erro na data");
        }
        return evento;
    }
}
