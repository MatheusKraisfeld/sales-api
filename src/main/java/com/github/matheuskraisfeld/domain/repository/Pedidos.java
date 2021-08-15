package com.github.matheuskraisfeld.domain.repository;

import com.github.matheuskraisfeld.domain.entity.Cliente;
import com.github.matheuskraisfeld.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);
}
