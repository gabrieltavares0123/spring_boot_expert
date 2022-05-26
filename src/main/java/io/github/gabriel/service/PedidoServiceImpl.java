package io.github.gabriel.service;

import io.github.gabriel.domain.entity.Cliente;
import io.github.gabriel.domain.entity.ItemPedido;
import io.github.gabriel.domain.entity.Pedido;
import io.github.gabriel.domain.entity.Produto;
import io.github.gabriel.domain.repository.ClienteRepository;
import io.github.gabriel.domain.repository.ItemPedidoRepository;
import io.github.gabriel.domain.repository.PedidoRepository;
import io.github.gabriel.domain.repository.ProdutoRepository;
import io.github.gabriel.rest.dto.ItemPedidoDto;
import io.github.gabriel.rest.dto.PedidoDto;
import io.github.gabriel.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido save(PedidoDto dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new ServiceException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemsPedido = convertItemsPedido(pedido, dto.getItems());

        pedidoRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    private List<ItemPedido> convertItemsPedido(Pedido pedido, List<ItemPedidoDto> items) {
        if (items.isEmpty()) {
            throw new ServiceException("Não é possível realizar um pedido sem itens.");
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new ServiceException(
                                            "Código de produto inválido: " + idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
