package io.github.gabriel.rest.controller;

import io.github.gabriel.domain.entity.Pedido;
import io.github.gabriel.rest.dto.PedidoDto;
import io.github.gabriel.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
