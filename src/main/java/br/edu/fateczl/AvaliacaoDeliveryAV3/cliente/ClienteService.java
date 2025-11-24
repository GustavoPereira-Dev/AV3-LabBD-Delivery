package br.edu.fateczl.AvaliacaoDeliveryAV3.cliente;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IService;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IService<Cliente, AtualizacaoCliente, String> {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente salvarOuAtualizar(AtualizacaoCliente dto) {
        Optional<Cliente> existenteOptional = clienteRepository.findById(dto.cpf());

        if (existenteOptional.isPresent()) {
            // Atualizando
            Cliente existente = existenteOptional.get();
            
            existente.setCpf(dto.cpf());
            existente.setNome(dto.nome());
            existente.setTelefone(dto.telefone());
            existente.setEndereco(dto.endereco());
            existente.setNumero(dto.numero());
            existente.setCep(dto.cep());
            existente.setCep(dto.pontoReferencia());
            
            return clienteRepository.save(existente);
        } else {
            Cliente novoCliente = new Cliente();
            novoCliente.setCpf(dto.cpf());
            novoCliente.setNome(dto.nome());
            novoCliente.setTelefone(dto.telefone());
            novoCliente.setEndereco(dto.endereco());
            novoCliente.setNumero(dto.numero());
            novoCliente.setCep(dto.cep());
            novoCliente.setPontoReferencia(dto.pontoReferencia());
            return clienteRepository.save(novoCliente);
        }
    }

    @Override
    public List<Cliente> procurarTodos() {
        return clienteRepository.findAll(Sort.by("nome").ascending());
    }

    @Override
    public void apagarPorId(String cpf) {
        if (!clienteRepository.existsById(cpf)) {
            throw new EntityNotFoundException("Cliente não encontrado com CPF: " + cpf);
        }
        clienteRepository.deleteById(cpf);
    }

    @Override
    public Optional<Cliente> procurarPorId(String cpf) {
        return clienteRepository.findById(cpf);
    }
    
    @Override
    public long contar() {
        return clienteRepository.count();
    }
}