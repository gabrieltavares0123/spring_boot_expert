package io.github.gabriel.domain.service;

import io.github.gabriel.core.base.exception.service.InvalidCodeException;
import io.github.gabriel.core.base.exception.service.NoDataException;
import io.github.gabriel.core.base.exception.service.PedidoNotFoundException;
import io.github.gabriel.domain.entity.Cliente;
import io.github.gabriel.domain.entity.ItemPedido;
import io.github.gabriel.domain.entity.Pedido;
import io.github.gabriel.domain.entity.Produto;
import io.github.gabriel.data.repository.ClienteRepository;
import io.github.gabriel.data.repository.ItemPedidoRepository;
import io.github.gabriel.data.repository.PedidoRepository;
import io.github.gabriel.data.repository.ProdutoRepository;
import io.github.gabriel.domain.enums.StatusPedido;
import io.github.gabriel.rest.dto.ItemPedidoDto;
import io.github.gabriel.rest.dto.PedidoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
                .orElseThrow(() -> new InvalidCodeException(
                        "S0001",
                        "Código de cliente inválido: " + idCliente
                ));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = convertItemsPedido(pedido, dto.getItems());

        pedidoRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    private List<ItemPedido> convertItemsPedido(Pedido pedido, List<ItemPedidoDto> items) {
        if (items.isEmpty()) {
            throw new NoDataException("S0002", "Não é possível realizar um pedido sem itens.");
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new InvalidCodeException(
                                            "S0003",
                                            "Código de produto inválido: " + idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido status) {
        pedidoRepository.findById(id)
                .map(p -> {
                    p.setStatus(status);
                    return pedidoRepository.save(p);
                })
                .orElseThrow(() -> new PedidoNotFoundException("S0004", "Pedido não encontrado."));
    }
}
