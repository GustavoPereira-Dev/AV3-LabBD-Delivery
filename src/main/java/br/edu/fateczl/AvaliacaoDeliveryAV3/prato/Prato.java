package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.Ingrediente;
import br.edu.fateczl.AvaliacaoDeliveryAV3.pedido.Pedido;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.Tipo;

@Entity
@Table(name = "prato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo")
    private Tipo tipo;

    @OneToMany(mappedBy = "prato")
    private Set<Pedido> pedidos;

    @ManyToMany
    @JoinTable(
            name = "prato_ingrediente",
            joinColumns = @JoinColumn(name = "id_prato"),
            inverseJoinColumns = @JoinColumn(name = "id_ingrediente")
    )
    private Set<Ingrediente> ingredientes = new HashSet<>();
    
    // Construtor e método de atualização não são práticos aqui
    // devido às relações complexas. O Service cuidará disso.
}