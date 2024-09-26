package com.example.petfriends_transporte.controller;

import com.example.petfriends_transporte.exception.ResourseNotFoundException;
import com.example.petfriends_transporte.model.Transporte;
import com.example.petfriends_transporte.payload.MessagePayload;
import com.example.petfriends_transporte.service.TransporteService;
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
@RequestMapping("/transporte")
@Slf4j
public class TransportadoraController {

    final TransporteService transporteService;

    public TransportadoraController(TransporteService transporteService) {
        this.transporteService = transporteService;
    }


    @GetMapping
    @Operation(summary = " -- LISTAGEM DE ITENS TRANSPORTADORAS")
    public List<Transporte> getAl(@RequestParam(required = false) String nome){
        return transporteService.lista();
    }

    @GetMapping("/{id}") @Operation (summary = " -- CONSULTA TRANSPORTADORA POR ID")
    public Optional<Transporte> getTransporte(@PathVariable Integer id){
        return transporteService.transportadoraId(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TRANSPORTADORA INSERIDA COM SUCESSO",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = Transporte.class))}
            )
    })

    @PostMapping@Operation (summary = " -- INSERIR UMA NOVA TRANSPORTADORA")
    public ResponseEntity<MessagePayload> insert(@RequestBody Transporte transporte){
        log.info("Inserindo uma trnsaportadora {}", transporte);
        transporteService.salva(transporte);
        return ResponseEntity.ok(new MessagePayload("Criado com sucesso"));
    }


    @PutMapping("/{id}")@Operation (summary = " -- ATUALIZANDO UMA TRANSPORTADORA")
    public ResponseEntity<MessagePayload> update(@PathVariable int id, @RequestBody Transporte transporte){
        try {
            transporteService.atualiza(id, transporte);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Atualizado com sucesso"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")@Operation (summary = " -- DELETANDO UMA TRANSPORTADORA")
    public ResponseEntity<MessagePayload> delete(@PathVariable int id){
        try {
            transporteService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Excluido"));
        } catch (ResourseNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }
}
