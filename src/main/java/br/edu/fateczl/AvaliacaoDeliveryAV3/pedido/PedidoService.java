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
    @Autowired private PedidoMapper pedidoMapper;
    @Autowired private ClienteService clienteService;
    @Autowired private PratoService pratoService;
    @Autowired private PorcaoService porcaoService;

    public Pedido salvarOuAtualizar(AtualizacaoPedido dto) {
        // 1. Validar Cliente
        Cliente cliente = clienteService.procurarPorId(dto.clienteCpf())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com CPF: " + dto.clienteCpf()));
        // 2. Validar Prato
        Prato prato = pratoService.procurarPorId(dto.pratoId())
                .orElseThrow(() -> new EntityNotFoundException("Prato não encontrado com ID: " + dto.pratoId()));
        // 3. Validar Porção
        Porcao porcao = porcaoService.procurarPorId(dto.porcaoId())
                .orElseThrow(() -> new EntityNotFoundException("Porção não encontrada com ID: " + dto.porcaoId()));

        Pedido pedido;
        if (dto.id() != null) {
            // Atualizando
            pedido = pedidoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + dto.id()));
            pedidoMapper.updateEntityFromDto(dto, pedido);
        } else {
            // Criando
            pedido = pedidoMapper.toEntityFromAtualizacao(dto);
        }

        // 4. Definir relações completas
        pedido.setCliente(cliente);
        pedido.setPrato(prato);
        pedido.setPorcao(porcao);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> procurarTodos() {
        return pedidoRepository.findAll(Sort.by("id").descending()); // Mais novos primeiro
    }

    public void apagarPorId(Integer id) {
        pedidoRepository.deleteById(id);
    }

    public Optional<Pedido> procurarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }
    
 // Exemplo no ClienteService.java (fazer o mesmo em todos os outros Services)
    public long contar() {
        return pedidoRepository.count();
    }
}