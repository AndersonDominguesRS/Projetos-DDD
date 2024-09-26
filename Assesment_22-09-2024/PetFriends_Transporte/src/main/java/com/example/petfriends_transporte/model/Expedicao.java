package com.example.petfriends_transporte.model;

import com.example.petfriends_transporte.enumerate.PedidoStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Expedicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "PEDIDO_ID")
    private Integer idPedido;
    @Column(name = "QTDE_VOLUME")
    private QtdeVolume qtdeVolume;
    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "operador_id", referencedColumnName = "id")
    private Operador codOperador;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transportadora_id", referencedColumnName = "id")
    private Transporte transportadora;
    @Column(name = "CODIGO_RASTREIO")
    private String codigoRastreio;
    @Column(name = "STATUS")
    private PedidoStatus pedidoStatus;
    @Column(name = "DATA_ALTERACAO")
    private Date dataAtualizao;

    public Expedicao() {
        this.dataAtualizao = new Date();
    }


//    public Expedicao(int idPedido, QtdeVolume qtdeVolume, Operador codOperador,
//                     Transporte transportadora, String codigoRastreio, PedidoStatus pedidoStatus) {
//        this.idPedido = idPedido;
//        this.qtdeVolume = qtdeVolume;
//        this.codOperador = codOperador;
//        this.transportadora = transportadora;
//        this.codigoRastreio = codigoRastreio;
//        this.pedidoStatus = pedidoStatus;
//        this.dataAtualizao = new Date();
//    }
}
