package org.example.imobsyss2.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.application.dtos.AtualizarStatusImovel;
import org.example.imobsyss2.application.dtos.ImovelRequest;
import org.example.imobsyss2.application.dtos.ImovelResponse;
import org.example.imobsyss2.application.services.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imoveis")
@Tag(name = "Imovel", description = "Grupo de api's responsável por controlar a consulta de imoveis no sistema")
public class ImovelController {

    @Autowired
    private ImovelService imovelService;

    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de imoveis", description = "Metodo responsavel em efetuar a consulta de todos os imoveis sem tag")
    public ResponseEntity<List<ImovelResponse>> listarTodos() {
        return ResponseEntity.ok(imovelService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de consulta de imóvel por id", description = "Metodo responsavel em buscar um imóvel específico")
    public ResponseEntity<ImovelResponse> buscarPorId(@PathVariable Long id) {
        ImovelResponse imovel = imovelService.buscarPorId(id);

        if (imovel != null) {
            return ResponseEntity.ok(imovel);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Metodo de criação de imóveis", description = "Metodo responsavel em efetuar a criação de imóveis")
    public ResponseEntity<?> criar(@RequestBody ImovelRequest request) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(imovelService.criar(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/statusImovel")
    @Operation(summary = "Metodo de atualização de status de imóveis", description = "Metodo responsavel em efetuar a atualização dos status dos imóveis")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusImovel statusImovelRequest) {
        if (imovelService.atualizarStatus(id, statusImovelRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/atualizarImovel")
    @Operation(summary = "Metodo de atualização de imóveis", description = "Metodo responsavel em efetuar a atualização de imóveis")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ImovelRequest request) {

        try {
            ImovelResponse atualizado = imovelService.atualizar(id, request);

            if (atualizado != null) {
                return ResponseEntity.ok(atualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluirImovel")
    @Operation(summary = "Metodo de exclusão de imóveis", description = "Metodo responsavel em efetuar a exclusão de imóveis")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (imovelService.excluir(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}