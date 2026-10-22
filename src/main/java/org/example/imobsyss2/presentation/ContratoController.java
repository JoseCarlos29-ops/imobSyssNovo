package org.example.imobsyss2.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.application.dtos.AtualizarStatusContrato;
import org.example.imobsyss2.application.services.ContratoService;
import org.example.imobsyss2.domain.entities.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contratos")
@Tag(name = "Contratos", description = "Grupo de api's responsável por controlar a consulta de contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de contratos", description = "Metodo responsavel em efetuar a consulta de todos os contratos sem tag")
    public ResponseEntity<List<Contrato>> listarTodos() {

        return ResponseEntity.ok(contratoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de consulta de contrato por id", description = "Metodo responsavel em buscar um contrato específico")
    public ResponseEntity<Contrato> buscarPorId(@PathVariable Long id) {

        Contrato contrato = contratoService.buscarPorId(id);

        if (contrato != null) {
            return ResponseEntity.ok(contrato);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Metodo de criação de contrato", description = "Metodo responsavel em efetuar a criação de contratos")
    public ResponseEntity<Contrato> criar(@RequestBody Contrato contrato) {

        return ResponseEntity.status(HttpStatus.CREATED).body(contratoService.criar(contrato));
    }

    @PatchMapping("/{id}/AtualizarStatusContrato")
    @Operation(summary = "Metodo de atualização de status de contratos", description = "Metodo responsavel em efetuar a atualização do status dos contratos")
    public ResponseEntity<Void> atualizarStatusContrato(@PathVariable Long id, @RequestBody AtualizarStatusContrato statusContratoRequest) {

        if (contratoService.atualizarStatusContrato(id, statusContratoRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/atualizarContratos")
    @Operation(summary = "Metodo de atualização de Contratos", description = "Metodo responsavel em efetuar a atualização de Contratos")
    public ResponseEntity<Contrato> atualizar(@PathVariable Long id, @RequestBody Contrato contrato) {

        Contrato atualizado = contratoService.atualizar(id, contrato);

        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/excluirContrato")
    @Operation(summary = "Metodo de exclusão de contrato", description = "Metodo responsavel em efetuar o cancelamento de contratos")
    public ResponseEntity<Void> excluirContrato(@PathVariable Long id) {

        if (contratoService.excluir(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}