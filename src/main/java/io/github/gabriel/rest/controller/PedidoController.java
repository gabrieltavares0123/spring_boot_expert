package io.github.gabriel.rest.controller;

import io.github.gabriel.domain.entity.ItemPedido;
import io.github.gabriel.domain.entity.Pedido;
import io.github.gabriel.domain.enums.StatusPedido;
import io.github.gabriel.domain.service.PedidoService;
import io.github.gabriel.rest.dto.AtualizacaoStatusPedidoDto;
import io.github.gabriel.rest.dto.InfoItemPedidoDto;
import io.github.gabriel.rest.dto.InfoPedidoDto;
import io.github.gabriel.rest.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDto dto) {
        Pedido pedido = service.save(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InfoPedidoDto getById(@PathVariable Integer id) {
        return service.obterPedidoCompleto(id)
                .map(this::converter)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado.")
                );
    }

    private InfoPedidoDto converter(Pedido pedido) {
        return InfoPedidoDto.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpfCliente(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .items(converter(pedido.getItens()))
                .status(pedido.getStatus().name())
                .build();
    }

    private List<InfoItemPedidoDto> converter(List<ItemPedido> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }

        return items.stream()
                .map(item -> InfoItemPedidoDto.builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
                ).collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestBody AtualizacaoStatusPedidoDto dto, @PathVariable Integer id) {
        String novoStatus = dto.getStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }
}
