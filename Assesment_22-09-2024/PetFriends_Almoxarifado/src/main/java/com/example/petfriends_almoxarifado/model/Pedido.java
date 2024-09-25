package com.example.petfriends_almoxarifado.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class Pedido implements Serializable {


    private int idPedido;

    public Pedido(int idPedido) {
        this.idPedido = idPedido;

    }

}
