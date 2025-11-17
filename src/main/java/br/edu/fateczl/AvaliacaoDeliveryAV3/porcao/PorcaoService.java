package br.edu.fateczl.AvaliacaoDeliveryAV3.porcao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PorcaoService {
    @Autowired private PorcaoRepository porcaoRepository;
    @Autowired private PorcaoMapper porcaoMapper;

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
    
    public List<Porcao> procurarTodos() {
        return porcaoRepository.findAll(Sort.by("tamanho").ascending());
    }
    
    public void apagarPorId(Integer id) {
    	porcaoRepository.deleteById(id);
    }
    
    public Optional<Porcao> procurarPorId(Integer id) {
        return porcaoRepository.findById(id);
    }
}