package io.github.gabriel.domain.service;

import io.github.gabriel.domain.entity.Pedido;
import io.github.gabriel.rest.dto.PedidoDto;

public interface PedidoService {
    Pedido save(PedidoDto dto);
}
