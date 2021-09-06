package com.github.matheuskraisfeld.domain.repository;

import com.github.matheuskraisfeld.domain.entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UsuarioRepository repository;

    @Test
    @DisplayName("Deve retornar usuario quando existir na base um usuario com o login informado")
    public void findUsuarioByLoginWhenExists(){

        Usuario usuario = Usuario.builder().login("root").senha("root").admin(true).build();
        entityManager.persist(usuario);

        Optional<Usuario> exists = repository.findByLogin(usuario.getLogin());

        assertThat(exists).isNotEmpty();
    }

    @Test
    @DisplayName("Deve retornar vazio quando buscar usuario que nao existe na base")
    public void findUsuarioByLoginWhenNotExists(){

        Usuario usuario = Usuario.builder().login("root").senha("root").admin(true).build();

        Optional<Usuario> exists = repository.findByLogin(usuario.getLogin());

        assertThat(exists).isEmpty();
    }

}
