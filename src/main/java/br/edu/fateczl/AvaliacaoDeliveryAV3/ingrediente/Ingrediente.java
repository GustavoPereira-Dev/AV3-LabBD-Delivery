package br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente;

import br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente.PratoIngrediente;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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
    
    @Column(length = 50, nullable = false)
    private String nome;
    
    @Column(name = "formatoapresentacao", length = 50, nullable = false)
    private String formatoApresentacao;

    @OneToMany(mappedBy = "ingrediente")
    private Set<PratoIngrediente> pratoIngredientes;
}