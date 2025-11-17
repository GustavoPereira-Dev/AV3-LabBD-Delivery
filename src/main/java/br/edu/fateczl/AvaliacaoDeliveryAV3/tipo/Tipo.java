package br.edu.fateczl.AvaliacaoDeliveryAV3.tipo;

import java.util.Set;

import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.Prato;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    
    @OneToMany(mappedBy = "tipo")
    private Set<Prato> pratos;
    
    public Tipo(AtualizacaoTipo dto) { this.nome = dto.nome(); }
    public void atualizarInformacoes(AtualizacaoTipo dto) {
        if (dto.nome() != null) this.nome = dto.nome();
    }
}