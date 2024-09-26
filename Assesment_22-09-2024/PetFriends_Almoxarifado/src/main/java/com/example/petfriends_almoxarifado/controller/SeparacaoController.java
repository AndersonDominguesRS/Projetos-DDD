package com.example.petfriends_almoxarifado.controller;

import com.example.petfriends_almoxarifado.exception.ResourseNotFoundException;
import com.example.petfriends_almoxarifado.model.Separacao;
import com.example.petfriends_almoxarifado.payload.MessagePayload;
import com.example.petfriends_almoxarifado.service.SeparacaoService;
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
@RequestMapping("/separacao")
@Slf4j
public class SeparacaoController {

    final SeparacaoService estoqueService;

    public SeparacaoController(SeparacaoService estoqueService) {
        this.estoqueService = estoqueService;
    }


    @GetMapping
    @Operation(summary = " -- LISTAGEM DE ITEM")
    public List<Separacao> getAl(@RequestParam(required = false) String nome){
        return estoqueService.lista();
    }

    @GetMapping("/{id}") @Operation (summary = " -- CONSULTA ITEM POR ID")
    public Optional<Separacao> getSeparacao(@PathVariable Integer id){
        return estoqueService.separacaoId(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ITEM INSERIDO COM SUCESSO",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = Separacao.class))}
            )
    })

    @PostMapping@Operation (summary = " -- INSERIR UM NOVO ITEM")
    public ResponseEntity<MessagePayload> insert(@RequestBody Separacao estoque){
        log.info("Inserindo um ITEM {}", estoque);
        estoqueService.salva(estoque);
        return ResponseEntity.ok(new MessagePayload("Criado com sucesso"));
    }


    @PutMapping("/{id}")@Operation (summary = " -- ATUALIZANDO UM ITEM")
    public ResponseEntity<MessagePayload> update(@PathVariable int id, @RequestBody Separacao estoque){
        try {
            estoqueService.atualiza(id, estoque);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Atualizado com sucesso"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")@Operation (summary = " -- DELETANDO UM ITEM")
    public ResponseEntity<MessagePayload> delete(@PathVariable int id){
        try {
            estoqueService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Excluido"));
        } catch (ResourseNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }
}
