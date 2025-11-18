package br.edu.fateczl.AvaliacaoDeliveryAV3.porcao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PorcaoService implements IService<Porcao, AtualizacaoPorcao, Integer> {
    @Autowired private PorcaoRepository porcaoRepository;
    @Autowired private PorcaoMapper porcaoMapper;

    @Override
    public Porcao salvarOuAtualizar(AtualizacaoPorcao dto) {
        if (dto.id() != null) {
        	Porcao existente = porcaoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado com ID: " + dto.id()));
        	porcaoMapper.updateEntityFromDto(dto, existente);
            return porcaoRepository.save(existente);
        } else {
        	Porcao novoTipo = porcaoMapper.toEntityFromAtualizacao(dto);
            return porcaoRepository.save(novoTipo);
        }
    }
    
    @Override
    public List<Porcao> procurarTodos() {
        return porcaoRepository.findAll(Sort.by("tamanho").ascending());
    }
    
    @Override
    public void apagarPorId(Integer id) {
    	porcaoRepository.deleteById(id);
    }
    
    @Override
    public Optional<Porcao> procurarPorId(Integer id) {
        return porcaoRepository.findById(id);
    }
    
    @Override
    public long contar() {
        return porcaoRepository.count();
    }
}