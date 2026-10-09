package org.example.imobsyss2.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.imobsyss2.application.dtos.LoginRequest;
import org.example.imobsyss2.application.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Controller de autenticação")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Método responsavel por efetuar o login do usuário!")
    public ResponseEntity<?> login(@RequestBody LoginRequest resquest) {

        var resultadoAutenticacaoRetornoToken = usuarioService.validarUsuarioAutenticadoRetornaToken(resquest);

        if (resultadoAutenticacaoRetornoToken != null) {
            return ResponseEntity.ok(resultadoAutenticacaoRetornoToken);
        }
        return ResponseEntity.badRequest().body("Usuário ou senha Invalido!");
    }
}