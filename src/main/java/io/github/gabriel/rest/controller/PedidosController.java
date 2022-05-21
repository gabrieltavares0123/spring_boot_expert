package io.github.gabriel.rest.controller;

import io.github.gabriel.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedido")
public class PedidosController {
    @Autowired
    private PedidoService pedidoService;
}
