package org.example.imobsyss2.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.dtos.AtualizarStatusCliente;
import org.example.imobsyss2.entities.*;
import org.example.imobsyss2.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "Grupo de api's responsável por controlar a consulta de Clientes no sistema")

public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    @Operation(summary =  "Metodo de consulta de lista de Clientes", description = "Metodo responsavel em efetuar a consulta de todos os clientes sem tag")
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "Metodo de criação de cliente", description = "Metodo responsavel em efetuar a criação de clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente){

        var clienteBanco = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteBanco);

    }

    @PatchMapping("/{id}/statusCliente")
    @Operation(summary = "Metodo de atualização de status de clientes", description = "Metodo responsavel em efetuar a atualização dos status dos clientes")
    public ResponseEntity<Void> atualizarStatusCliente(@PathVariable Long id, @RequestBody AtualizarStatusCliente statusClienteRequest ){
        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
        if(clienteBanco!= null ){
            clienteBanco.setStatusCliente(statusClienteRequest.statusCliente());
            clienteRepository.save(clienteBanco);
            return  ResponseEntity.ok().build();
        }
        return  ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/atualizarImovel")
    @Operation(summary = "Metodo de atualização de imóveis", description = "Metodo responsavel em efetuar a atualização de imóveis")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {

        try {
            Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
            if (clienteBanco != null){
                clienteBanco.setStatusCliente(cliente.getStatusCliente());
                clienteBanco.setNome(cliente.getNome());
                clienteBanco.setCpf(cliente.getCpf());
                clienteBanco.setEndereco(cliente.getEndereco());
                clienteBanco.setEmail(cliente.getEmail());
                cliente.setTelefone(cliente.getTelefone());
                clienteRepository.save(clienteBanco);


                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluirCliente")
    @Operation(summary = "Metodo de exclusão de cliente", description = "Metodo responsavel em efetuar a exclusão de clientes")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
        if(clienteBanco!= null ){
            clienteBanco.setStatusCliente(EnumStatusCliente.INATIVO);
            clienteRepository.save(clienteBanco);
            return  ResponseEntity.ok().build();
        }
        return  ResponseEntity.notFound().build();
    }



}


