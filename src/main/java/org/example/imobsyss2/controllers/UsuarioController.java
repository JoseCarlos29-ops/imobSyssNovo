package org.example.imobsyss2.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.dtos.AtualizarStatusRequest;
import org.example.imobsyss2.entities.EnumStatusUsuario;
import org.example.imobsyss2.entities.Usuario;
import org.example.imobsyss2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Grupo de api's responsável por controlar a consulta de usuarios do sistema")

public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation (summary =  "Metodo de consulta de lista de usuarios", description = "Metodo responsavel em efetuar a consulta de todos os usuarios sem tag")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco!= null){
            return ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    @Operation(summary = "Metodo de criação de usuarios", description = "Metodo responsavel em efetuar a criação de usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {

        var usuarioBanco = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioBanco);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco!= null ){
            usuarioBanco.setStatus(statusRequest.status());
            usuarioRepository.save(usuarioBanco);
            return  ResponseEntity.ok().build();
        }
        return  ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {

        try {        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if (usuarioBanco != null){
                usuarioBanco.setStatus(usuario.getStatus());
                usuarioBanco.setNome(usuario.getNome());
                usuarioBanco.setSenha(usuario.getSenha());
                usuarioBanco.setCpf(usuario.getCpf());
                usuarioBanco.setEmail(usuario.getEmail());
                usuarioRepository.save(usuarioBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluir")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco!= null ){
            usuarioBanco.setStatus(EnumStatusUsuario.EXCLUIDO);
            usuarioRepository.save(usuarioBanco);
            return  ResponseEntity.ok().build();
        }
        return  ResponseEntity.notFound().build();
    }
}
