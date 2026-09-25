package org.example.imobsyss2.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.dtos.AtualizarStatusImovel;
import org.example.imobsyss2.dtos.AtualizarStatusRequest;
import org.example.imobsyss2.entities.EnumStatusImovel;
import org.example.imobsyss2.entities.EnumStatusUsuario;
import org.example.imobsyss2.entities.Imovel;
import org.example.imobsyss2.entities.Usuario;
import org.example.imobsyss2.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/imoveis")
@Tag(name = "Imovel", description = "Grupo de api's responsável por controlar a consulta de imoveis no sistema")

public class ImovelController {

    @Autowired
    private ImovelRepository imovelRepository;

    @GetMapping
    @Operation(summary =  "Metodo de consulta de lista de imoveis", description = "Metodo responsavel em efetuar a consulta de todos os imoveis sem tag")
    public ResponseEntity<?> listarTodos(){

        return ResponseEntity.ok(imovelRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Metodo de consulta de imóvel por id", description = "Metodo responsavel em buscar um imóvel específico")
    public ResponseEntity<Imovel> buscarPorId(@PathVariable Long id){
        Imovel imovelBanco = imovelRepository.findById(id).orElse(null);
        if(imovelBanco != null){
            return ResponseEntity.ok(imovelBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Metodo de criação de imóveis", description = "Metodo responsavel em efetuar a criação de imóveis")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Imovel> criar(@RequestBody Imovel imovel){

        var imovelBanco = imovelRepository.save(imovel);
        return ResponseEntity.ok(imovelBanco);

    }

    @PatchMapping("/{id}/statusImovel")
    @Operation(summary = "Metodo de atualização de status de imóveis", description = "Metodo responsavel em efetuar a atualização dos status dos imóveis")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusImovel statusImovelRequest) {
        Imovel imovelBanco = imovelRepository.findById(id).orElse(null);
        if (imovelBanco != null) {
            imovelBanco.setStatus(statusImovelRequest.statusImovel());
            imovelRepository.save(imovelBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}/atualizarImovel")
    @Operation(summary = "Metodo de atualização de imóveis", description = "Metodo responsavel em efetuar a atualização de imóveis")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Imovel imovel) {

        try {
            Imovel imovelBanco = imovelRepository.findById(id).orElse(null);
            if (imovelBanco != null){
                imovelBanco.setStatus(imovel.getStatus());
                imovelBanco.setEndereco(imovel.getEndereco());
                imovelBanco.setTipo(imovel.getTipo());
                imovelBanco.setValor(imovel.getValor());
                imovelBanco.setArea(imovel.getArea());
                imovelBanco.setQuartos(imovel.getQuartos());
                imovelBanco.setBanheiros(imovel.getBanheiros());
                imovelBanco.setDescricao(imovel.getDescricao());
                imovelBanco.setProprietario(imovel.getProprietario());
                imovelRepository.save(imovelBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluirImovel")
    @Operation(summary = "Metodo de exclusão de imóveis", description = "Metodo responsavel em efetuar a exclusão de imóveis")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        Imovel imovelBanco = imovelRepository.findById(id).orElse(null);
        if(imovelBanco!= null ){
            imovelBanco.setStatus(EnumStatusImovel.INDISPONIVEL);
            imovelRepository.save(imovelBanco);
            return  ResponseEntity.ok().build();
        }
        return  ResponseEntity.notFound().build();
    }


}