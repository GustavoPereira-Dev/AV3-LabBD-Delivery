package br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente;

import java.util.Set;

import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.Prato;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "ingrediente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    
    @Column(name = "formatoapresentacao")
    private String formatoApresentacao;

    @ManyToMany(mappedBy = "ingredientes")
    private Set<Prato> pratos;
    
    public Ingrediente(AtualizacaoIngrediente dto) {
        this.nome = dto.nome();
        this.formatoApresentacao = dto.formatoApresentacao();
    }
    public void atualizarInformacoes(AtualizacaoIngrediente dto) {
        if (dto.nome() != null) this.nome = dto.nome();
        if (dto.formatoApresentacao() != null) this.formatoApresentacao = dto.formatoApresentacao();
    }
}