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
public class PratoService implements IService<Prato, AtualizacaoPrato, String> {
    @Autowired private PratoRepository pratoRepository;
    @Autowired private PratoMapper pratoMapper;
    @Autowired private TipoService tipoService;

    @Override
    public Prato salvarOuAtualizar(AtualizacaoPrato dto) {
        // 1. Valida e busca o Tipo (ManyToOne)
        Tipo tipo = tipoService.procurarPorId(dto.tipoId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado com ID: " + dto.tipoId()));

    	Prato prato;

        // Verifica se é uma ATUALIZAÇÃO (DTO tem ID) ou INSERÇÃO (DTO sem ID)
        if (dto.id() != null && !dto.id().isEmpty()) {
            // Atualização: Busca o existente
            prato = pratoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Prato não encontrado"));
        } else {
            // Inserção: Cria um novo
            prato = new Prato();
            
            // AQUI ESTÁ A MÁGICA: Chama a Procedure no banco para gerar o ID
            String novoId = pratoRepository.sp_gera_id_prato();
            prato.setId(novoId);
        }

        // Atualiza os demais campos
        prato.setNome(dto.nome());
        prato.setValor(dto.valor());
        prato.setTipo(tipo);
        
        // ... lógica para buscar e setar o Tipo (semelhante ao que você já tem) ...
        
        return pratoRepository.save(prato);
    }

    @Override
    public List<Prato> procurarTodos() {
        return pratoRepository.findAll(Sort.by("nome").ascending());
    }

    @Override
    public void apagarPorId(String id) {
        pratoRepository.deleteById(id);
    }

    @Override
    public Optional<Prato> procurarPorId(String id) {
        return pratoRepository.findById(id);
    }
    
    public long contar() {
        return pratoRepository.count();
    }
}