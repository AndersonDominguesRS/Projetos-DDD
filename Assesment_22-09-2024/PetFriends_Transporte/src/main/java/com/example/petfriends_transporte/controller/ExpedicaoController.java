package com.example.petfriends_transporte.controller;

import com.example.petfriends_transporte.exception.ResourseNotFoundException;
import com.example.petfriends_transporte.model.Expedicao;
import com.example.petfriends_transporte.payload.MessagePayload;
import com.example.petfriends_transporte.service.ExpedicaoService;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/expedicao")
@Slf4j
public class ExpedicaoController {

    final ExpedicaoService expedicaoService;

    public ExpedicaoController(ExpedicaoService expedicaoService) {
        this.expedicaoService = expedicaoService;
    }


    @GetMapping
    @Operation(summary = " -- LISTAGEM DE ITENS EXPEDIDOS")
    public List<Expedicao> getAl(@RequestParam(required = false) String nome){
        return expedicaoService.lista();
    }

    @GetMapping("/{id}") @Operation (summary = " -- CONSULTA EXPEDIÇÃO POR ID")
    public Optional<Expedicao> getExpedicao(@PathVariable Integer id){
      return expedicaoService.expedicaoId(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "EXPEDIÇÃO INSERIDA COM SUCESSO",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = Expedicao.class))}
            )
    })

    @PostMapping@Operation (summary = " -- INSERIR UMA NOVA EXPEDIÇÃO")
    public ResponseEntity<MessagePayload> insert(@RequestBody Expedicao expedicao){
        log.info("Inserindo uma nova expedicao {}", expedicao);
        expedicaoService.salva(expedicao);
        return ResponseEntity.ok(new MessagePayload("Criado com sucesso"));
    }


    @PutMapping("/{id}")@Operation (summary = " -- ATUALIZANDO UMA EXPEDIÇÃO")
    public ResponseEntity<MessagePayload> update(@PathVariable int id, @RequestBody Expedicao expedicao){
        try {
            expedicaoService.atualiza(id, expedicao);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Atualizado com sucesso"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")@Operation (summary = " -- DELETANDO UMA EXPEDIÇÃO")
    public ResponseEntity<MessagePayload> delete(@PathVariable int id){
        try {
            expedicaoService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Excluido"));
        } catch (ResourseNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }
}
