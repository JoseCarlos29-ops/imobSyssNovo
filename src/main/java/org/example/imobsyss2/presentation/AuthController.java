package org.example.imobsyss2.presentation;

import org.example.imobsyss2.application.dtos.LoginRequest;
import org.example.imobsyss2.application.dtos.LoginResponse;
import org.example.imobsyss2.application.services.UsuarioService;
import org.example.imobsyss2.application.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name="Autenticação", description = "Controller de autenticação")
public class AuthController {


    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("/login")
    @Operation(description = "Método de login", summary = "Autenticação de usuarios")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        var resultadoAutenticacaoRetornoToken = usuarioService.validarUsuarioAutenticadoeRetornaToken(loginRequest);


        if(resultadoAutenticacaoRetornoToken != null){



            return ResponseEntity.ok(resultadoAutenticacaoRetornoToken);

        }

        return ResponseEntity.badRequest().body("Usuario ou senha invalido!");
    }

}
