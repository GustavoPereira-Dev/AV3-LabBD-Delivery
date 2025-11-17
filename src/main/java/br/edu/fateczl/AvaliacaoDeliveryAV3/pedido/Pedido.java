package br.edu.fateczl.AvaliacaoDeliveryAV3.pedido;

import br.edu.fateczl.AvaliacaoDeliveryAV3.cliente.Cliente;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.Porcao;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.Prato;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpf_cliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prato")
    private Prato prato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_porcao")
    private Porcao porcao;
}