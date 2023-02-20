package br.com.alura.forum.service;

import br.com.alura.forum.dto.AtualizarTopicoDTO;
import br.com.alura.forum.dto.NovoTopicoDTO;
import br.com.alura.forum.dto.TopicoCompletoDTO;
import br.com.alura.forum.dto.TopicoDTO;
import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.ICursoRepository;
import br.com.alura.forum.repository.ITopicoRepository;
import br.com.alura.forum.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private ITopicoRepository topicoRepository;

    @Autowired
    private ICursoRepository cursoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public Page<TopicoDTO> buscarTodos(Pageable paginacao) {
        Page<Topico> topicos = topicoRepository.findAll(paginacao);
        return TopicoDTO.converter(topicos);
    }

    public Page<TopicoDTO> buscarPorCurso(String nomeCurso, Pageable paginacao) {
        Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
        return TopicoDTO.converter(topicos);
    }

    public Topico criarTopico(NovoTopicoDTO novoTopicoDTO) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Curso> curso = cursoRepository.findById(novoTopicoDTO.getCurso());
        Topico topico = new Topico(novoTopicoDTO.getTitulo(), novoTopicoDTO.getMensagem(), curso.get(), usuario);
        return topicoRepository.save(topico);
    }

    public ResponseEntity<TopicoCompletoDTO> buscarTopicoPorId(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isPresent()) {
            return ResponseEntity.ok(new TopicoCompletoDTO(topico.get()));
        }

        return ResponseEntity.notFound().build();

    }

    @Transactional
    public Topico atualizarTopico(Long id, AtualizarTopicoDTO atualizarTopicoDTO) {
        Topico topico = topicoRepository.getReferenceById(id);
        if(atualizarTopicoDTO.getTitulo() != null && !atualizarTopicoDTO.getTitulo().isBlank()) {
            topico.setTitulo(atualizarTopicoDTO.getTitulo());
        }
        if(atualizarTopicoDTO.getMensagem() != null && !atualizarTopicoDTO.getMensagem().isBlank()) {
            topico.setMensagem(atualizarTopicoDTO.getMensagem());
        }

        return topico;
    }

    public void excluirTopico(Long id) {
        topicoRepository.deleteById(id);
    }
}
