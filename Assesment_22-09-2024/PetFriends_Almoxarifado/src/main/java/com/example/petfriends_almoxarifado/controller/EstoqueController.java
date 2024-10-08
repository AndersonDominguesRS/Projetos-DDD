package com.example.petfriends_almoxarifado.controller;


import com.example.petfriends_almoxarifado.exception.ResourseNotFoundException;
import com.example.petfriends_almoxarifado.model.Estoque;
import com.example.petfriends_almoxarifado.payload.MessagePayload;
import com.example.petfriends_almoxarifado.service.EstoqueService;
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
@RequestMapping("/estoque")
@Slf4j
public class EstoqueController {

    final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }


    @GetMapping
    @Operation(summary = " -- LISTAGEM DE ITENS ESTOQUE")
    public List<Estoque> getAl(@RequestParam(required = false) String nome){
        return estoqueService.lista();
    }

    @GetMapping("/{id}") @Operation (summary = " -- CONSULTA ESTOQUE POR ID")
    public Optional<Estoque> getEstoque(@PathVariable Integer id){
        return estoqueService.estoqueId(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ESTOQUE INSERIDO COM SUCESSO",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = Estoque.class))}
            )
    })

    @PostMapping@Operation (summary = " -- INSERIR UM NOVA ESTOQUE")
    public ResponseEntity<MessagePayload> insert(@RequestBody Estoque estoque){
        log.info("Inserindo um estoque {}", estoque);
        estoqueService.salva(estoque);
        return ResponseEntity.ok(new MessagePayload("Criado com sucesso"));
    }


    @PutMapping("/{id}")@Operation (summary = " -- ATUALIZANDO UM ESTOQUE")
    public ResponseEntity<MessagePayload> update(@PathVariable int id, @RequestBody Estoque estoque){
        try {
            estoqueService.atualiza(id, estoque);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Atualizado com sucesso"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")@Operation (summary = " -- DELETANDO UM ESTOQUE")
    public ResponseEntity<MessagePayload> delete(@PathVariable int id){
        try {
            estoqueService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Excluido"));
        } catch (ResourseNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }
}
