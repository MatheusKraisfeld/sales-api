package com.github.matheuskraisfeld.service;

import com.github.matheuskraisfeld.domain.entity.Pedido;
import com.github.matheuskraisfeld.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}
