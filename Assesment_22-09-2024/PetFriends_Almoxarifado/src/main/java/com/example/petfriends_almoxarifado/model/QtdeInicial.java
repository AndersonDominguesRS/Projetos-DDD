package com.example.petfriends_almoxarifado.model;

import java.io.Serializable;

public class QtdeInicial implements Serializable {

    private final int qteInicial;

    public QtdeInicial() {
        this.qteInicial = 0;
    }

    public QtdeInicial(int qteInicial) {

        if (qteInicial < 1) {
            throw new IllegalArgumentException("UM NOVO PRODUTO DEVE SER ADICIONADO AO ESTOQUE" +
                    "COM A QUANTIDADE INICIAL MINIMA DE UM ITEM");
        }

        this.qteInicial = qteInicial;
    }
    public int getQteInicial() {
        return qteInicial;
    }
}
