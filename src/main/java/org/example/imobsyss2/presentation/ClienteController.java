package org.example.imobsyss2.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.application.dtos.AtualizarStatusCliente;
import org.example.imobsyss2.application.dtos.ClienteRequest;
import org.example.imobsyss2.application.dtos.ClienteResponse;
import org.example.imobsyss2.application.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "Grupo de api's responsável por controlar a consulta de Clientes no sistema")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de Clientes", description = "Metodo responsavel em efetuar a consulta de todos os clientes sem tag")
    public ResponseEntity<List<ClienteResponse>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de consulta de cliente por id", description = "Metodo responsavel em buscar um cliente específico")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        ClienteResponse cliente = clienteService.buscarPorId(id);

        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Metodo de criação de cliente", description = "Metodo responsavel em efetuar a criação de clientes")
    public ResponseEntity<ClienteResponse> criar(@RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criar(request));
    }

    @PatchMapping("/{id}/statusCliente")
    @Operation(summary = "Metodo de atualização de status de clientes", description = "Metodo responsavel em efetuar a atualização dos status dos clientes")
    public ResponseEntity<Void> atualizarStatusCliente(@PathVariable Long id, @RequestBody AtualizarStatusCliente statusClienteRequest) {
        if (clienteService.atualizarStatusCliente(id, statusClienteRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/atualizarCliente")
    @Operation(summary = "Metodo de atualização de clientes", description = "Metodo responsavel em efetuar a atualização de clientes")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @RequestBody ClienteRequest request) {
        ClienteResponse atualizado = clienteService.atualizar(id, request);

        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/excluirCliente")
    @Operation(summary = "Metodo de exclusão de cliente", description = "Metodo responsavel em efetuar a exclusão de clientes")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (clienteService.excluir(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}