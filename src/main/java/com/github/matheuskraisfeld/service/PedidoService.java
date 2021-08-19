package com.github.matheuskraisfeld.service;

import com.github.matheuskraisfeld.domain.entity.Pedido;
import com.github.matheuskraisfeld.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
