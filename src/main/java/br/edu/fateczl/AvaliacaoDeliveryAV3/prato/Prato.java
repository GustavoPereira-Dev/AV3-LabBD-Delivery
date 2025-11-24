package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;

import br.edu.fateczl.AvaliacaoDeliveryAV3.pedido.Pedido;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente.PratoIngrediente;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.Tipo;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "prato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Prato {

    @Id
    @Column(length = 10)
    private String id;
    
    @Column(length = 50, nullable = false)
    private String nome;

    @Column(precision = 7, nullable = false)
    private Double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", nullable = false)
    private Tipo tipo;

    @OneToMany(mappedBy = "prato")
    private Set<Pedido> pedidos;

    @OneToMany(mappedBy = "prato", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PratoIngrediente> pratoIngredientes = new HashSet<>();


}