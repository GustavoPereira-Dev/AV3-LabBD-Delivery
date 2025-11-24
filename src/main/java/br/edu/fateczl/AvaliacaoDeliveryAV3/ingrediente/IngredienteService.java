package br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class IngredienteService implements IService<Ingrediente, AtualizacaoIngrediente, Integer>{
    @Autowired private IngredienteRepository ingredienteRepository;

    @Override
    public Ingrediente salvarOuAtualizar(AtualizacaoIngrediente dto) {
        if (dto.id() != null) {
        	Ingrediente existente = ingredienteRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado com ID: " + dto.id()));
        	
        	
        	existente.setId(dto.id());
        	existente.setNome(dto.nome());
        	existente.setFormatoApresentacao(dto.formatoApresentacao());
            return ingredienteRepository.save(existente);
        } else {
        	Ingrediente novoIngrediente = new Ingrediente();
        	novoIngrediente.setId(dto.id());
        	novoIngrediente.setNome(dto.nome());
        	novoIngrediente.setFormatoApresentacao(dto.formatoApresentacao());
            return ingredienteRepository.save(novoIngrediente);
        }
    }
    
    @Override
    public List<Ingrediente> procurarTodos() {
        return ingredienteRepository.findAll(Sort.by("nome").ascending());
    }
    
    @Override
    public void apagarPorId(Integer id) {
    	ingredienteRepository.deleteById(id);
    }
    
    @Override
    public Optional<Ingrediente> procurarPorId(Integer id) {
        return ingredienteRepository.findById(id);
    }
    
    @Override
    public long contar() {
        return ingredienteRepository.count();
    }
}