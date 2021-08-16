package com.github.matheuskraisfeld.service.impl;

import com.github.matheuskraisfeld.domain.repository.Pedidos;
import com.github.matheuskraisfeld.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private Pedidos repository;

    public PedidoServiceImpl(Pedidos repository) {
        this.repository = repository;
    }
}
