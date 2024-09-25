package com.example.petfriends_transporte.eventos;


import com.example.petfriends_transporte.enumerate.PedidoStatus;

import java.io.Serializable;
import java.util.Date;

public class EstadoPedidoMudouTransportadora implements Serializable {
    
    private Integer idPedido;
    private PedidoStatus estado;
    private Date momento;
    
    public EstadoPedidoMudouTransportadora() {
    }

    public EstadoPedidoMudouTransportadora(Integer idPedido, PedidoStatus estado) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.momento = new Date();
    }
    
    public EstadoPedidoMudouTransportadora(Integer idPedido, PedidoStatus estado, Date momento) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.momento = momento;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public PedidoStatus getEstado() {
        return estado;
    }

    public void setEstado(PedidoStatus estado) {
        this.estado = estado;
    }

    public Date getMomento() {
        return momento;
    }

    public void setMomento(Date momento) {
        this.momento = momento;
    }

    @Override
    public String toString() {
        return "EstadoPedidoMudouTransportadora{" + "idPedido=" + idPedido + ", estado=" + estado + ", momento=" + momento + '}';
    }
}
