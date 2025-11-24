package br.edu.fateczl.AvaliacaoDeliveryAV3.pedido;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.fateczl.AvaliacaoDeliveryAV3.cliente.Cliente;
import br.edu.fateczl.AvaliacaoDeliveryAV3.cliente.ClienteService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.Porcao;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.PorcaoService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.Prato;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.PratoService;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService implements IService<Pedido, AtualizacaoPedido, Integer> {
    @Autowired private PedidoRepository pedidoRepository;
    // Removido: @Autowired private PedidoMapper pedidoMapper;
    @Autowired private ClienteService clienteService;
    @Autowired private PratoService pratoService;
    @Autowired private PorcaoService porcaoService;

    public Pedido salvarOuAtualizar(AtualizacaoPedido dto) {
        Cliente cliente = clienteService.procurarPorId(dto.clienteCpf())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com CPF: " + dto.clienteCpf()));
        Prato prato = pratoService.procurarPorId(dto.pratoId())
                .orElseThrow(() -> new EntityNotFoundException("Prato não encontrado com ID: " + dto.pratoId()));
        Porcao porcao = porcaoService.procurarPorId(dto.porcaoId())
                .orElseThrow(() -> new EntityNotFoundException("Porção não encontrada com ID: " + dto.porcaoId()));

        Pedido pedido;
        if (dto.id() != null) {
            pedido = pedidoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + dto.id()));
            
            pedido.setValor(dto.valor());
        } else {
            pedido = new Pedido();
            
            pedido.setValor(dto.valor());
        }

        pedido.setCliente(cliente);
        pedido.setPrato(prato);
        pedido.setPorcao(porcao);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> procurarTodos() {
        return pedidoRepository.findAll(Sort.by("id").descending());
    }

    public void apagarPorId(Integer id) {
        pedidoRepository.deleteById(id);
    }

    public Optional<Pedido> procurarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }
    
    public long contar() {
        return pedidoRepository.count();
    }
}