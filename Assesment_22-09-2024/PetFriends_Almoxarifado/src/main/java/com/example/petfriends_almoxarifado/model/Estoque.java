package com.example.petfriends_almoxarifado.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "TB_ESTOQUE")
@Data
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Produto produto;
    @Column(name = "ULTIMA_ALTERACAO")
    @Temporal(TemporalType.DATE)
    private Date ultimaAlteracao;
    @Column(name = "QUANTIDADE_INICIAL")
    private QtdeInicial qtdeInicial;

}
