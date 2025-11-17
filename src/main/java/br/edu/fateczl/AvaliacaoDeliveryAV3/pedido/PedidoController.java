package br.edu.fateczl.AvaliacaoDeliveryAV3.pedido;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fateczl.AvaliacaoDeliveryAV3.cliente.ClienteService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.PorcaoService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.PratoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired private PedidoService pedidoService;
    @Autowired private PedidoMapper pedidoMapper;
    @Autowired private ClienteService clienteService;
    @Autowired private PratoService pratoService;
    @Autowired private PorcaoService porcaoService;

    // Método auxiliar
    private void carregarDadosParaFormulario(Model model) {
        model.addAttribute("clientes", clienteService.procurarTodos());
        model.addAttribute("pratos", pratoService.procurarTodos());
        model.addAttribute("porcoes", porcaoService.procurarTodos());
    }

    @GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaPedidos", pedidoService.procurarTodos());
        return "pedido/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Integer id, Model model) {
        AtualizacaoPedido dto;
        if (id != null) {
            Pedido pedido = pedidoService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
            dto = pedidoMapper.toAtualizacaoDto(pedido);
        } else {
            dto = new AtualizacaoPedido(null, 0.0, null, null, null);
        }
        model.addAttribute("pedido", dto);
        carregarDadosParaFormulario(model);
        return "pedido/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("pedido") @Valid AtualizacaoPedido dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pedido", dto);
            carregarDadosParaFormulario(model);
            return "pedido/formulario";
        }
        try {
            Pedido salvo = pedidoService.salvarOuAtualizar(dto);
            String mensagem = dto.id() != null
                    ? "Pedido #" + salvo.getId() + " atualizado com sucesso!"
                    : "Pedido #" + salvo.getId() + " criado com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/pedido";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("pedido", dto);
            carregarDadosParaFormulario(model);
            return "pedido/formulario";
        }
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deletar(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            pedidoService.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message", "O pedido #" + id + " foi apagado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Não foi possível apagar o pedido.");
        }
        return "redirect:/pedido";
    }
}