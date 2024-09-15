package com.example.tp2_ddd_15_09_2024.infraMessage;

import com.example.tp2_ddd_15_09_2024.model.Produto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProdutoDeserializer extends StdDeserializer<Produto> {

    public ProdutoDeserializer() {
        super(Produto.class);
    }

    @Override
    public Produto deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        Produto produto = new Produto();

        produto.setCodigo(node.get("codigo").asInt());
        produto.setNome(node.get("nome").asText());
        produto.setValor(node.get("valor").asInt());
        produto.setEstado(node.get("estado").asText());
        produto.setId(node.get("id").asInt());

        return produto;
    }
}
