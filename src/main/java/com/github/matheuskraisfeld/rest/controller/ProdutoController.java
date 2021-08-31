package com.github.matheuskraisfeld.rest.controller;

import com.github.matheuskraisfeld.domain.entity.Produto;
import com.github.matheuskraisfeld.domain.repository.Produtos;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos produtos;

    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado."),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado.")
    })
    public Produto getProdutoById(@PathVariable @ApiParam("Id do produto") Integer id){
        return produtos
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto salvo com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public Produto save(@RequestBody @Valid Produto produto){
        return produtos.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Excluir produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto excluído com sucesso."),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado.")
    })
    public void delete(@PathVariable @ApiParam("Id do produto") Integer id){
        produtos
                .findById(id)
                .map(produto -> {
                    produtos.delete(produto);
                    return produto;
                })
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualizar dados de um produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado.")
    })
    public void update(@PathVariable @ApiParam("Id do produto") Integer id,
                       @RequestBody @Valid Produto produto){
        produtos
                .findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtos.save(produto);
                    return produtoExistente;
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping
    @ApiOperation("Pesquisar produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado."),
            @ApiResponse(code = 404, message = "Produto não encontrado para o filtro informado.")
    })
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return produtos.findAll(example);
    }
}
