package com.github.matheuskraisfeld.domain.repository;

import com.github.matheuskraisfeld.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
