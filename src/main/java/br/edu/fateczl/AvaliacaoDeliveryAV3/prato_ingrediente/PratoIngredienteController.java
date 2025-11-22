package br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.IngredienteService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.PorcaoService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.PratoService;

@Controller
@RequestMapping("/ficha-tecnica")
public class PratoIngredienteController {

    @Autowired private PratoIngredienteService service;
    @Autowired private PratoService pratoService;
    @Autowired private IngredienteService ingredienteService;
    @Autowired private PorcaoService porcaoService;

    @GetMapping
    public String gerenciar(Model model) {
        // 1. Carrega a lista principal da tabela
        model.addAttribute("listaFichas", service.procurarTodos());
        
        // 2. CORREÇÃO: Inicializa o objeto 'ficha' para o formulário oculto no modal
        // Sem isso, o Thymeleaf dá erro ao tentar renderizar o th:object="${ficha}"
        model.addAttribute("ficha", new AtualizacaoPratoIngrediente(null, null, null));
        
        // 3. Carrega os dados dos Dropdowns (Selects) para o formulário inicial
        carregarCombos(model);
        
        return "pratoingrediente/gerenciar";
    }

    @GetMapping("/formulario")
    public String formulario(Model model) {
        model.addAttribute("ficha", new AtualizacaoPratoIngrediente(null, null, null));
        carregarCombos(model);
        return "pratoingrediente/gerenciar :: modalFormContent";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("ficha") @Valid AtualizacaoPratoIngrediente dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            carregarCombos(model);
            return "pratoingrediente/gerenciar :: modalFormContent";
        }
        try {
            service.salvar(dto);
            redirectAttributes.addFlashAttribute("message", "Ingrediente vinculado ao prato com sucesso!");
            return "fragmentos/modalSuccess";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao vincular: " + e.getMessage());
            carregarCombos(model);
            return "pratoingrediente/gerenciar :: modalFormContent";
        }
    }

    @GetMapping("/delete")
    @Transactional
    public String deletar(@RequestParam String pratoId, 
                          @RequestParam Integer ingredienteId, 
                          @RequestParam Integer porcaoId,
                          RedirectAttributes redirectAttributes) {
        try {
            service.apagar(pratoId, ingredienteId, porcaoId);
            redirectAttributes.addFlashAttribute("message", "Vínculo removido!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao remover vínculo.");
        }
        return "redirect:/ficha-tecnica";
    }

    private void carregarCombos(Model model) {
        model.addAttribute("pratos", pratoService.procurarTodos());
        model.addAttribute("ingredientes", ingredienteService.procurarTodos());
        model.addAttribute("porcoes", porcaoService.procurarTodos());
    }
}