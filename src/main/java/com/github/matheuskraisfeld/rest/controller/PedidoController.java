package com.github.matheuskraisfeld.rest.controller;

import com.github.matheuskraisfeld.domain.entity.ItemPedido;
import com.github.matheuskraisfeld.domain.entity.Pedido;
import com.github.matheuskraisfeld.domain.enums.StatusPedido;
import com.github.matheuskraisfeld.rest.dto.AtualizacaoStatusPedidoDTO;
import com.github.matheuskraisfeld.rest.dto.InformacoesItemPedidoDTO;
import com.github.matheuskraisfeld.rest.dto.InformacoesPedidoDTO;
import com.github.matheuskraisfeld.rest.dto.PedidoDTO;
import com.github.matheuskraisfeld.service.PedidoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    @ApiOperation("Salvar pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido salvo com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    @ApiOperation("Obter informações do pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido encontrado."),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado.")
    })
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atualiza status de um pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Status atualizado."),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado.")
    })
    public void updateStatus(@PathVariable Integer id,
                             @RequestBody AtualizacaoStatusPedidoDTO dto){

        String statusPedido = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(statusPedido));

    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InformacoesItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacoesItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build())
                .collect(Collectors.toList());
    }
}
