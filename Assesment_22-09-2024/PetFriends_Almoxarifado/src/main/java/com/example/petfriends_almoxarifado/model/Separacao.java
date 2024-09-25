package com.example.petfriends_almoxarifado.model;

import com.example.petfriends_almoxarifado.enumerate.PedidoStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "TB_SEPARACAO")
@Data
public class Separacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private List<Produto> listProdutos;
    private int idPedido;
    private PedidoStatus pedidoStatus;
    private Date dataRecebimento;

//    public Separacao() {
//        this.pedidoStatus = PedidoStatus.EM_PREPARACAO;
//        this.dataRecebimento = new Date();
//    }

    public Separacao(int idPedido, PedidoStatus pedidoStatus) {
        this.dataRecebimento = new Date();
        this.idPedido = idPedido;
        this.pedidoStatus = pedidoStatus;
    }

    public Separacao() {

    }
}
