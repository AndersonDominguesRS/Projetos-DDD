package com.example.petfriends_almoxarifado.mesageSerializer;


import com.example.petfriends_almoxarifado.eventos.EstadoPedidoMudouAlmox;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


import java.io.IOException;
import java.text.SimpleDateFormat;

public class AlmoxSerializer extends StdSerializer<EstadoPedidoMudouAlmox> {

    public AlmoxSerializer() {
        super(EstadoPedidoMudouAlmox.class);
    }

    @Override
    public void serialize(EstadoPedidoMudouAlmox evento, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("idPedido", evento.getIdPedido());
        jgen.writeStringField("estado", evento.getEstado().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        String data = sdf.format(evento.getMomento());
        jgen.writeStringField("momento", data);
    }
}
