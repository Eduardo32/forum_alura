package br.com.alura.forum.repository;

import br.com.alura.forum.model.Curso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@DataJpaTest
@ActiveProfiles("test")
public class ICursoRepositoryTest {

    @Autowired
    private ICursoRepository cursoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void findByNomeTeste() {
        Curso curso = new Curso("HTML 5", "Programação");
        em.persist(curso);

        Curso cursoEncontrado = cursoRepository.findByNome("HTML 5");
        Assertions.assertNotNull(cursoEncontrado);
        Assertions.assertEquals(curso.getNome(), cursoEncontrado.getNome());
    }
}