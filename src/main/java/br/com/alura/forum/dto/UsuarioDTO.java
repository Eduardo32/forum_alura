package br.com.alura.forum.dto;

import br.com.alura.forum.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public static List<UsuarioDTO> converter(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }
}
