package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.IngredienteService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.TipoService;

@Controller
@RequestMapping("/prato")
public class PratoController {

    @Autowired private PratoService pratoService;
    // Removido: @Autowired private PratoMapper pratoMapper;
    @Autowired private TipoService tipoService;
    @Autowired private IngredienteService ingredienteService;

    private void carregarDadosParaFormulario(Model model) {
        model.addAttribute("tipos", tipoService.procurarTodos());
        model.addAttribute("ingredientes", ingredienteService.procurarTodos());
    }

    @GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaPratos", pratoService.procurarTodos());
        return "prato/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) String id, Model model) {
        AtualizacaoPrato dto;
        if (id != null) {
            Prato prato = pratoService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Prato não encontrado"));
            
            // Conversão Manual: Entity -> DTO
            // Nota: O 5º argumento é null pois ingredientes agora são via Ficha Técnica
            dto = new AtualizacaoPrato(
                prato.getId(), 
                prato.getNome(), 
                prato.getTipo().getId(),
                prato.getValor()
            );
        } else {
            dto = new AtualizacaoPrato(null, null, null, null);
        }
        model.addAttribute("prato", dto);
        carregarDadosParaFormulario(model);
        return "prato/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("prato") @Valid AtualizacaoPrato dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("prato", dto);
            carregarDadosParaFormulario(model);
            return "prato/formulario";
        }
        try {
            Prato salvo = pratoService.salvarOuAtualizar(dto);
            String mensagem = dto.id() != null
                    ? "Prato '" + salvo.getNome() + "' atualizado com sucesso!"
                    : "Prato '" + salvo.getNome() + "' criado com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/prato";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("prato", dto);
            carregarDadosParaFormulario(model);
            return "prato/formulario";
        }
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deletar(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
            pratoService.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message", "O prato " + id + " foi apagado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Não foi possível apagar. Verifique se o prato está em uso.");
        }
        return "redirect:/prato";
    }
}