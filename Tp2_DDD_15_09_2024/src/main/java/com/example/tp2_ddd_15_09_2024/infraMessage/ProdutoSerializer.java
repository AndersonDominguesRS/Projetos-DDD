package com.example.tp2_ddd_15_09_2024.infraMessage;

import com.example.tp2_ddd_15_09_2024.model.Produto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class ProdutoSerializer extends StdSerializer<Produto> {



    public ProdutoSerializer() {
        super(Produto.class);
    }

    @Override
    public void serialize(Produto produto, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", produto.getId());
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        jgen.writeStringField("nome", produto.getNome());
        jgen.writeStringField("estado", produto.getEstado());
        jgen.writeNumberField("codigo", produto.getCodigo());
        jgen.writeNumberField("valor", produto.getValor());
    }

}

