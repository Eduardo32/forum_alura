package br.com.alura.forum.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NovoTopicoDTO {

    @NotEmpty
    @Length(min = 5)
    private String titulo;

    @NotEmpty @Length(min = 10)
    private String mensagem;

    @NotNull
    private Long curso;

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Long getCurso() {
        return curso;
    }
}
