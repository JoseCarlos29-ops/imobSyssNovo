package org.example.imobsyss2.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.dtos.AtualizarStatusContrato;
import org.example.imobsyss2.entities.*;
import org.example.imobsyss2.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

    @RequestMapping("/contratos")
    @Tag(name = "Contratos", description = "Grupo de api's responsável por controlar a consulta de contratos")
    public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;

    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de contratos", description = "Metodo responsavel em efetuar a consulta de todos os contratos sem tag")
    public ResponseEntity<?> listarTodos() {

        return ResponseEntity.ok(contratoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Contrato> criar(@RequestBody Contrato contrato) {

        var contratoBanco = contratoRepository.save(contrato);
        return ResponseEntity.ok(contratoBanco);

    }

    @PatchMapping("{id}/AtualizarStatusContrato")
    public ResponseEntity<Void> AtualizarStatusCliente(@PathVariable Long id, @RequestBody AtualizarStatusContrato statusContratoResquest) {
        Contrato contratoBanco = contratoRepository.findById(id).orElse(null);
        if(contratoBanco!=null){
            contratoBanco.setStatusContrato(statusContratoResquest.statusContrato());
            contratoRepository.save(contratoBanco);

            return ResponseEntity.ok().build();
        }
       return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/atualizarContratos")
    @Operation(summary = "Metodo de atualização de Contratos", description = "Metodo responsavel em efetuar a atualização de Contratos")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Contrato contrato) {

        try {
            Contrato contratoBanco = contratoRepository.findById(id).orElse(null);
            if (contratoBanco != null) {
                contratoBanco.setTipo(contratoBanco.getTipo());
                contratoBanco.setStatusContrato(contratoBanco.getStatusContrato());
                contratoBanco.setDataContrato(contratoBanco.getDataContrato());
                contratoBanco.setValorContrato(contratoBanco.getValorContrato());
                contratoBanco.setValorComissao(contratoBanco.getValorComissao());

                contratoRepository.save(contratoBanco);


                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        @DeleteMapping("/{id}/excluirContrato")
        @Operation(summary = "Metodo de exclusão de contrato", description = "Metodo responsavel em efetuar a exclusão de clientes")
        public ResponseEntity<Void> excluirContrato(@PathVariable Long id){
            Contrato contratoBanco = contratoRepository.findById(id).orElse(null);
            if(contratoBanco!= null ){
                contratoBanco.setStatusContrato(EnumStatusContrato.CANCELADO);
                contratoRepository.save(contratoBanco);
                return  ResponseEntity.ok().build();
            }
            return  ResponseEntity.notFound().build();
        }



    }




