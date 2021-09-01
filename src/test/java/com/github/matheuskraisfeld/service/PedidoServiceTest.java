package com.github.matheuskraisfeld.service;

import com.github.matheuskraisfeld.domain.enums.StatusPedido;
import com.github.matheuskraisfeld.domain.repository.Pedidos;
import com.github.matheuskraisfeld.exception.PedidoNaoEncontradoException;
import com.github.matheuskraisfeld.service.impl.PedidoServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceTest {

    @InjectMocks
    PedidoServiceImpl service;

    @Mock
    Pedidos repository;

    @Test
    public void testErroAtualizaStatusIdInvalido(){
        Assertions.assertThrows(
                PedidoNaoEncontradoException.class,
                () -> service.atualizaStatus(0, StatusPedido.REALIZADO));
    }

    @Test
    public void testErroAtualizaStatusInvalido(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizaStatus(1, StatusPedido.valueOf("TESTE")));
    }



}
