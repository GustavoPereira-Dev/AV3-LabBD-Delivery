package br.edu.fateczl.AvaliacaoDeliveryAV3.porcao;

import java.util.Set;

import br.edu.fateczl.AvaliacaoDeliveryAV3.pedido.Pedido;
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
@Table(name = "porcao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Porcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tamanho;
    
    @OneToMany(mappedBy = "porcao")
    private Set<Pedido> pedidos;

    public Porcao(AtualizacaoPorcao dto) { this.tamanho = dto.tamanho(); }
    public void atualizarInformacoes(AtualizacaoPorcao dto) {
        if (dto.tamanho() != null) this.tamanho = dto.tamanho();
    }
}