package org.example.imobsyss2.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.application.dtos.AtualizarStatusImovel;
import org.example.imobsyss2.application.services.ImovelService;
import org.example.imobsyss2.domain.entities.Imovel;
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
    public ResponseEntity<List<Imovel>> listarTodos() {

        return ResponseEntity.ok(imovelService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de consulta de imóvel por id", description = "Metodo responsavel em buscar um imóvel específico")
    public ResponseEntity<Imovel> buscarPorId(@PathVariable Long id) {

        Imovel imovel = imovelService.buscarPorId(id);

        if (imovel != null) {
            return ResponseEntity.ok(imovel);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Metodo de criação de imóveis", description = "Metodo responsavel em efetuar a criação de imóveis")
    public ResponseEntity<Imovel> criar(@RequestBody Imovel imovel) {

        return ResponseEntity.status(HttpStatus.CREATED).body(imovelService.criar(imovel));
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
    public ResponseEntity<Imovel> atualizar(@PathVariable Long id, @RequestBody Imovel imovel) {

        Imovel atualizado = imovelService.atualizar(id, imovel);

        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
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