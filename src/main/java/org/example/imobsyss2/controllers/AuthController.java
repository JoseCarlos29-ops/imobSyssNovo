package org.example.imobsyss2.controllers;

import org.example.imobsyss2.dtos.LoginRequest;
import org.example.imobsyss2.dtos.LoginResponse;
import org.example.imobsyss2.repository.UsuarioRepository;
import org.example.imobsyss2.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/auth")
@Tag(name="Autenticação", description = "Controller de autenticação")
public class AuthController {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(description = "Método de login", summary = "Autenticação de usuarios")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){


        if(usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())){
            //Gera Token
            var token = tokenService.gerarToken(loginRequest.email());

            return ResponseEntity.ok(new LoginResponse(token));
        }

        return ResponseEntity.badRequest().body("Usuario ou senha invalido!");
    }

}
