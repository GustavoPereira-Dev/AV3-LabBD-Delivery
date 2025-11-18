package br.edu.fateczl.AvaliacaoDeliveryAV3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.fateczl.AvaliacaoDeliveryAV3.cliente.ClienteService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.IngredienteService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.pedido.PedidoService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.PorcaoService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.PratoService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente.PratoIngredienteService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.TipoService;

@Controller
@RequestMapping("/") // Mapeia a raiz e /home
public class HomeController {

    @Autowired private PedidoService pedidoService;
    @Autowired private ClienteService clienteService;
    @Autowired private PratoService pratoService;
    @Autowired private IngredienteService ingredienteService;
    @Autowired private TipoService tipoService;
    @Autowired private PorcaoService porcaoService;
    @Autowired private PratoIngredienteService fichaService;

    @GetMapping
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Adiciona os contadores ao modelo
        model.addAttribute("qtdPedidos", pedidoService.contar());
        model.addAttribute("qtdClientes", clienteService.contar());
        model.addAttribute("qtdPratos", pratoService.contar());
        model.addAttribute("qtdIngredientes", ingredienteService.contar());
        model.addAttribute("qtdTipos", tipoService.contar());
        model.addAttribute("qtdPorcoes", porcaoService.contar());
        model.addAttribute("qtdFichas", fichaService.contar());
        
        return "home/dashboard";
    }
}