package org.example.imobsyss2.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.application.dtos.CriarTipoImovelRequest;
import org.example.imobsyss2.application.dtos.TipoImovelResponse;
import org.example.imobsyss2.application.services.TipodeImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-imovel")
@Tag(name = "Tipo de Imovel", description = "Grupo de api's responsável por controlar os tipos de imóveis (Residencial, Comercial, Rural...)")
public class TipoImovelController {

    @Autowired
    private TipodeImovelService tipodeImovelService;

    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de tipos de imovel", description = "Metodo responsavel em efetuar a consulta de todos os tipos de imovel da empresa")
    public ResponseEntity<List<TipoImovelResponse>> listarTodos() {
        return ResponseEntity.ok(tipodeImovelService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de consulta de tipo de imovel por id", description = "Metodo responsavel em buscar um tipo de imovel específico")
    public ResponseEntity<TipoImovelResponse> buscarPorId(@PathVariable Long id) {
        TipoImovelResponse tipo = tipodeImovelService.buscarPorId(id);

        if (tipo != null) {
            return ResponseEntity.ok(tipo);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Metodo de criação de tipo de imovel", description = "Metodo responsavel em efetuar a criação de tipos de imovel")
    public ResponseEntity<?> criar(@RequestBody CriarTipoImovelRequest request) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tipodeImovelService.criar(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Metodo de atualização de tipo de imovel", description = "Metodo responsavel em efetuar a atualização de tipos de imovel")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CriarTipoImovelRequest request) {

        try {
            TipoImovelResponse atualizado = tipodeImovelService.atualizar(id, request);

            if (atualizado != null) {
                return ResponseEntity.ok(atualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Metodo de exclusão de tipo de imovel", description = "Metodo responsavel em efetuar a exclusão de tipos de imovel que nao estao em uso")
    public ResponseEntity<?> excluir(@PathVariable Long id) {

        try {
            if (tipodeImovelService.excluir(id)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
