package br.edu.fateczl.AvaliacaoDeliveryAV3.pedido;

import java.time.LocalDate;
import java.time.ZoneId;

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
    
    @Column(nullable = false)
    private Float valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpf_cliente", nullable = false)
    private Cliente cliente;
    
    // Mapeado para a coluna 'data' do tipo DATE no SQL
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prato") // SQL não tem NOT NULL explícito, mas é boa prática
    private Prato prato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_porcao", nullable = false)
    private Porcao porcao;
    
    @PrePersist
    public void prePersist() {
        if (this.data == null) {
            this.data = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        }
    }
}