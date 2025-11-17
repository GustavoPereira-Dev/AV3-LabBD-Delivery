package br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class IngredienteService {
    @Autowired private IngredienteRepository ingredienteRepository;
    @Autowired private IngredienteMapper ingredienteMapper;

    public Ingrediente salvarOuAtualizar(AtualizacaoIngrediente dto) {
        if (dto.id() != null) {
        	Ingrediente existente = ingredienteRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado com ID: " + dto.id()));
        	ingredienteMapper.updateEntityFromDto(dto, existente);
            return ingredienteRepository.save(existente);
        } else {
        	Ingrediente novoTipo = ingredienteMapper.toEntityFromAtualizacao(dto);
            return ingredienteRepository.save(novoTipo);
        }
    }
    
    public List<Ingrediente> procurarTodos() {
        return ingredienteRepository.findAll(Sort.by("tamanho").ascending());
    }
    
    public void apagarPorId(Integer id) {
    	ingredienteRepository.deleteById(id);
    }
    
    public Optional<Ingrediente> procurarPorId(Integer id) {
        return ingredienteRepository.findById(id);
    }
}