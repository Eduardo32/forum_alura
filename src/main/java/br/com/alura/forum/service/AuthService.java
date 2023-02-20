package br.com.alura.forum.service;

import br.com.alura.forum.config.security.TokenServise;
import br.com.alura.forum.dto.LoginDTO;
import br.com.alura.forum.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServise tokenService;

    public TokenDTO autenticar(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken dadosLogin = loginDTO.converter();
        Authentication authenticate = authenticationManager.authenticate(dadosLogin);
        String token = tokenService.gerarToken(authenticate);

        return new TokenDTO(token, "Bearer");
    }
}
