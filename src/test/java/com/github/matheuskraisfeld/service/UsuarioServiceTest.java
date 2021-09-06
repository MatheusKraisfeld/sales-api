package com.github.matheuskraisfeld.service;

import com.github.matheuskraisfeld.domain.entity.Usuario;
import com.github.matheuskraisfeld.domain.repository.UsuarioRepository;
import com.github.matheuskraisfeld.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @InjectMocks
    UsuarioServiceImpl service;

    @Mock
    UsuarioRepository repository;

    @Test
    @DisplayName("Deve salvar usuario com sucesso")
    public void saveUsuarioTest(){

        Usuario usuario = Usuario.builder().login("root").senha("root").admin(true).build();
        Mockito.when(repository.save(usuario))
                .thenReturn(
                        Usuario.builder()
                                .id(1).login("root").senha("root").admin(true)
                                .build()
                );

        Usuario saved = service.salvar(usuario);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getLogin()).isEqualTo("root");
        assertThat(saved.getSenha()).isEqualTo("root");
        assertThat(saved.isAdmin()).isEqualTo(true);

    }

    @Test
    @DisplayName("Deve retornar erro de campos obrigatorios")
    public void saveUsuarioEmptyTest() throws Exception{

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.salvar(new Usuario())
                );

    }

}
