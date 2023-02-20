package br.com.alura.forum.controller;

import br.com.alura.forum.dto.AtualizarTopicoDTO;
import br.com.alura.forum.dto.NovoTopicoDTO;
import br.com.alura.forum.dto.TopicoCompletoDTO;
import br.com.alura.forum.dto.TopicoDTO;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDTO> getTopicos(@RequestParam(required = false) String nomeCurso,
                                      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable paginacao) {
        if(nomeCurso == null) {
            return topicoService.buscarTodos(paginacao);
        }
        return topicoService.buscarPorCurso(nomeCurso, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoCompletoDTO> getTopicoCompleto(@PathVariable Long id) {
        return topicoService.buscarTopicoPorId(id);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> criar(@Valid @RequestBody NovoTopicoDTO novoTopicoDTO, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoService.criarTopico(novoTopicoDTO);

        URI uri = uriBuilder.path("/api/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody AtualizarTopicoDTO atualizarTopicoDTO) {
        Topico topico = topicoService.atualizarTopico(id, atualizarTopicoDTO);
        return ResponseEntity.ok().body(new TopicoDTO(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity deletar(@PathVariable Long id) {
        topicoService.excluirTopico(id);
        return ResponseEntity.ok().build();
    }
}
