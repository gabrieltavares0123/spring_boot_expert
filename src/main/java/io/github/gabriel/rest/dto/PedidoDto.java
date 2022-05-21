package io.github.gabriel.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDto {
    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDto> items;
}
