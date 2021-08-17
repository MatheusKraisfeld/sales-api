package com.github.matheuskraisfeld.rest.controller;

import com.github.matheuskraisfeld.domain.entity.Pedido;
import com.github.matheuskraisfeld.rest.dto.PedidoDTO;
import com.github.matheuskraisfeld.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }
}
