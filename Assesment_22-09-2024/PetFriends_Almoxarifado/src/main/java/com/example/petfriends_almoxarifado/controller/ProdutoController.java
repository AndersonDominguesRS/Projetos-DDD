package com.example.petfriends_almoxarifado.controller;


import com.example.petfriends_almoxarifado.exception.ResourseNotFoundException;
import com.example.petfriends_almoxarifado.model.Produto;
import com.example.petfriends_almoxarifado.payload.MessagePayload;
import com.example.petfriends_almoxarifado.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
@Slf4j
public class ProdutoController {

    final ProdutoService estoqueService;

    public ProdutoController(ProdutoService estoqueService) {
        this.estoqueService = estoqueService;
    }


    @GetMapping
    @Operation(summary = " -- LISTAGEM DE ITENS PRODUTO")
    public List<Produto> getAl(@RequestParam(required = false) String nome){
        return estoqueService.lista();
    }

    @GetMapping("/{id}") @Operation (summary = " -- CONSULTA PRODUTO POR ID")
    public Optional<Produto> getProduto(@PathVariable Integer id){
        return estoqueService.produtoId(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PRODUTO INSERIDA COM SUCESSO",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = Produto.class))}
            )
    })

    @PostMapping@Operation (summary = " -- INSERIR UM NOVA PRODUTO")
    public ResponseEntity<MessagePayload> insert(@RequestBody Produto estoque){
        log.info("Inserindo um produto {}", estoque);
        estoqueService.salva(estoque);
        return ResponseEntity.ok(new MessagePayload("Criado com sucesso"));
    }


    @PutMapping("/{id}")@Operation (summary = " -- ATUALIZANDO UM PRODUTO")
    public ResponseEntity<MessagePayload> update(@PathVariable int id, @RequestBody Produto estoque){
        try {
            estoqueService.atualiza(id, estoque);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Atualizado com sucesso"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")@Operation (summary = " -- DELETANDO UM PRODUTO")
    public ResponseEntity<MessagePayload> delete(@PathVariable int id){
        try {
            estoqueService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Excluido"));
        } catch (ResourseNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }
}
