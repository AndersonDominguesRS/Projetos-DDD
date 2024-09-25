package com.example.petfriends_transporte.model;


import java.io.Serializable;

public class QtdeVolume implements Serializable {

    private final int qtdeVolume;

    public QtdeVolume(){
        this.qtdeVolume = 0;    }


    public QtdeVolume(int qtdeVolume) {

        if (qtdeVolume < 1){
            throw new IllegalArgumentException("O VALOR INICIAL DO VOLUME NÃO PODE SER MENOR QUE 1");
        }
        this.qtdeVolume = qtdeVolume;
    }
    public int getQtdeVolume() {
        return qtdeVolume;
    }
}
