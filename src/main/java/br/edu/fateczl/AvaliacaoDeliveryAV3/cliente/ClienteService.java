package br.edu.fateczl.AvaliacaoDeliveryAV3.cliente;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public Cliente salvarOuAtualizar(AtualizacaoCliente dto) {
        // CPF é a chave.
        // Tenta buscar pelo CPF. Se existir, atualiza. Se não, cria.
        Optional<Cliente> existenteOptional = clienteRepository.findById(dto.cpf());

        if (existenteOptional.isPresent()) {
            // Atualizando
            Cliente existente = existenteOptional.get();
            clienteMapper.updateEntityFromDto(dto, existente);
            return clienteRepository.save(existente);
        } else {
            // Criando
            Cliente novoCliente = clienteMapper.toEntityFromAtualizacao(dto);
            return clienteRepository.save(novoCliente);
        }
    }

    public List<Cliente> procurarTodos() {
        return clienteRepository.findAll(Sort.by("nome").ascending());
    }

    public void apagarPorId(String cpf) {
        if (!clienteRepository.existsById(cpf)) {
            throw new EntityNotFoundException("Cliente não encontrado com CPF: " + cpf);
        }
        clienteRepository.deleteById(cpf);
    }

    public Optional<Cliente> procurarPorId(String cpf) {
        return clienteRepository.findById(cpf);
    }
}