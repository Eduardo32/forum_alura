package br.com.alura.forum.dto;

import br.com.alura.forum.model.Topico;
import br.com.alura.forum.model.enums.EStatusTopico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopicoCompletoDTO extends TopicoDTO {
    private EStatusTopico status;
    private List<RespostaDTO> respostas = new ArrayList<>();

    public TopicoCompletoDTO(Topico topico) {
        super(topico);
        this.status = topico.getStatus();
        this.respostas.addAll(topico.getRespostas().stream().map(RespostaDTO::new).collect(Collectors.toList()));
    }

    public List<RespostaDTO> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaDTO> respostas) {
        this.respostas = respostas;
    }

    public EStatusTopico getStatus() {
        return status;
    }
}
