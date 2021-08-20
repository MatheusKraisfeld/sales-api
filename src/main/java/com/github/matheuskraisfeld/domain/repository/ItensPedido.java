package com.github.matheuskraisfeld.domain.repository;

import com.github.matheuskraisfeld.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer> {
}
