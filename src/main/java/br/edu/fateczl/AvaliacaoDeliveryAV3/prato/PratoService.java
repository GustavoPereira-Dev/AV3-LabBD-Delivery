package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.Ingrediente;
import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.IngredienteRepository;
import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.Tipo;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.TipoService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PratoService implements IService<Prato, AtualizacaoPrato, Integer> {
    @Autowired private PratoRepository pratoRepository;
    @Autowired private PratoMapper pratoMapper;
    @Autowired private TipoService tipoService;
    @Autowired private IngredienteRepository ingredienteRepository; // Usamos Repository pois é só busca

    @Override
    public Prato salvarOuAtualizar(AtualizacaoPrato dto) {
        // 1. Valida e busca o Tipo (ManyToOne)
        Tipo tipo = tipoService.procurarPorId(dto.tipoId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado com ID: " + dto.tipoId()));

        // 2. Valida e busca os Ingredientes (ManyToMany)
        Set<Ingrediente> ingredientes = new HashSet<>();
        if (dto.ingredienteIds() != null && !dto.ingredienteIds().isEmpty()) {
            ingredientes.addAll(ingredienteRepository.findAllById(dto.ingredienteIds()));
            // Validação simples para garantir que todos os IDs foram encontrados
            if (ingredientes.size() != dto.ingredienteIds().size()) {
                throw new EntityNotFoundException("Um ou mais ingredientes não foram encontrados.");
            }
        }

        Prato prato;
        if (dto.id() != null) {
            // Atualizando
            prato = pratoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Prato não encontrado com ID: " + dto.id()));
            pratoMapper.updateEntityFromDto(dto, prato);
        } else {
            // Criando
            prato = pratoMapper.toEntityFromAtualizacao(dto);
        }

        // 3. Define as relações na entidade
        prato.setTipo(tipo);
        prato.setIngredientes(ingredientes);

        return pratoRepository.save(prato);
    }

    @Override
    public List<Prato> procurarTodos() {
        return pratoRepository.findAll(Sort.by("nome").ascending());
    }

    @Override
    public void apagarPorId(Integer id) {
        pratoRepository.deleteById(id);
    }

    @Override
    public Optional<Prato> procurarPorId(Integer id) {
        return pratoRepository.findById(id);
    }
    
    public long contar() {
        return pratoRepository.count();
    }
}