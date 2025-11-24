package br.edu.fateczl.AvaliacaoDeliveryAV3.porcao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/porcao")
public class PorcaoController {

    @Autowired private PorcaoService porcaoService;
    // Removido: @Autowired private PorcaoMapper porcaoMapper;

    @GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaPorcoes", porcaoService.procurarTodos());
        return "porcao/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Integer id, Model model) {
        AtualizacaoPorcao dto;
        if (id != null) {
            Porcao item = porcaoService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Porção não encontrada"));
            
            // Conversão Manual: Entity -> DTO
            dto = new AtualizacaoPorcao(
                item.getId(), 
                item.getTamanho(), 
                item.getValor()
            );
        } else {
            dto = new AtualizacaoPorcao(null, null, null);
        }
        model.addAttribute("porcao", dto);
        return "porcao/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("porcao") @Valid AtualizacaoPorcao dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("porcao", dto);
            return "porcao/formulario";
        }
        try {
            Porcao salvo = porcaoService.salvarOuAtualizar(dto);
            String mensagem = dto.id() != null
                    ? "Porção '" + salvo.getTamanho() + "' atualizada com sucesso!"
                    : "Porção '" + salvo.getTamanho() + "' criada com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/porcao";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/porcao/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
        }
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deletar(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            porcaoService.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message", "A porção " + id + " foi apagada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Não foi possível apagar. Verifique se o item está em uso.");
        }
        return "redirect:/porcao";
    }
}