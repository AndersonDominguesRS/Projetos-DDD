package com.example.petfriends_transporte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transportadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cnpj;

    public Transportadora() {}

    public Transportadora(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }
}
