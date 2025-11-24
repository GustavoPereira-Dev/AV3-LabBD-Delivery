package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.Tipo;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.TipoService;

import java.util.List;
import java.util.Optional;

@Service
public class PratoService implements IService<Prato, AtualizacaoPrato, String> {
    @Autowired private PratoRepository pratoRepository;
    @Autowired private TipoService tipoService;

    @Override
    public Prato salvarOuAtualizar(AtualizacaoPrato dto) {
        Tipo tipo = tipoService.procurarPorId(dto.tipoId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado com ID: " + dto.tipoId()));

    	Prato prato;

        if (dto.id() != null && !dto.id().isEmpty()) {
            prato = pratoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Prato não encontrado"));
        } else {
            prato = new Prato();
            
            String novoId = pratoRepository.sp_gera_id_prato();
            prato.setId(novoId);
        }

        // Atualiza os demais campos
        prato.setNome(dto.nome());
        prato.setValor(dto.valor());
        prato.setTipo(tipo);
        
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