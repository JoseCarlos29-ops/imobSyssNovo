package org.example.imobsyss2.application.services;


import io.swagger.v3.oas.annotations.Operation;
import org.example.imobsyss2.application.dtos.LoginRequest;
import org.example.imobsyss2.application.dtos.LoginResponse;
import org.example.imobsyss2.application.dtos.UsuarioResponse;
import org.example.imobsyss2.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired

    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    public LoginResponse validarUsuarioAutenticadoeRetornaToken( LoginRequest request){


        if(usuarioRepository.existsUsuarioByEmailAndSenha(request.email(), request.senha())){
            //Gera Token, olhar no git do professor o pq está diferente
            var token = tokenService.gerarToken(request.email());

            return new  LoginResponse(token);
        }

        return null;
    }

    @GetMapping
    @Operation(summary =  "Metodo de consulta de lista de usuarios", description = "Metodo responsavel em efetuar a consulta de todos os usuarios sem tag")
    public List<UsuarioResponse> listarTodosUsuariosTable(){

        return  usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

}



