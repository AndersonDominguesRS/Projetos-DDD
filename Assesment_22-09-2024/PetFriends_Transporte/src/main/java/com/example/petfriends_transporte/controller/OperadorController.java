package com.example.petfriends_transporte.controller;

import com.example.petfriends_transporte.exception.ResourseNotFoundException;
import com.example.petfriends_transporte.model.Operador;
import com.example.petfriends_transporte.payload.MessagePayload;
import com.example.petfriends_transporte.service.OperadorService;
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
@RequestMapping("/operador")
@Slf4j
public class OperadorController {

    final OperadorService operadorService;

    public OperadorController(OperadorService operadorService) {
        this.operadorService = operadorService;
    }


    @GetMapping
    @Operation(summary = " -- LISTAGEM DE ITENS OPERADOR")
    public List<Operador> getAl(@RequestParam(required = false) String nome){
        return operadorService.lista();
    }

    @GetMapping("/{id}") @Operation (summary = " -- CONSULTA OPERADOR POR ID")
    public Optional<Operador> getOperador(@PathVariable Integer id){
        return operadorService.operadorId(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OPERADOR INSERIDA COM SUCESSO",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = Operador.class))}
            )
    })

    @PostMapping@Operation (summary = " -- INSERIR UM NOVA OPERADOR")
    public ResponseEntity<MessagePayload> insert(@RequestBody Operador operador){
        log.info("Inserindo um operador {}", operador);
        operadorService.salva(operador);
        return ResponseEntity.ok(new MessagePayload("Criado com sucesso"));
    }


    @PutMapping("/{id}")@Operation (summary = " -- ATUALIZANDO UM OPERADOR")
    public ResponseEntity<MessagePayload> update(@PathVariable int id, @RequestBody Operador operador){
        try {
            operadorService.atualiza(id, operador);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Atualizado com sucesso"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")@Operation (summary = " -- DELETANDO UM OPERADOR")
    public ResponseEntity<MessagePayload> delete(@PathVariable int id){
        try {
            operadorService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Excluido"));
        } catch (ResourseNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }
}
