package br.com.alura.forum.controller;

import br.com.alura.forum.dto.LoginDTO;
import br.com.alura.forum.dto.TokenDTO;
import br.com.alura.forum.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            TokenDTO tokenDTO = authService.autenticar(loginDTO);
            return ResponseEntity.ok(tokenDTO);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
