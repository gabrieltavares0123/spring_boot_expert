package io.github.gabriel.domain.service;

import io.github.gabriel.domain.entity.Pedido;
import io.github.gabriel.domain.enums.StatusPedido;
import io.github.gabriel.rest.dto.PedidoDto;

import java.util.Optional;

public interface PedidoService {
    Pedido save(PedidoDto dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido status);
}
