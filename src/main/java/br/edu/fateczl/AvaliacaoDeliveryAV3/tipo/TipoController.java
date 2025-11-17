package br.edu.fateczl.AvaliacaoDeliveryAV3.tipo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tipo")
public class TipoController {

    @Autowired private TipoService tipoService;
    @Autowired private TipoMapper tipoMapper;

    @GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaItens", tipoService.procurarTodos());
        return "tipo/listagem"; // Usará um HTML genérico
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Integer id, Model model) {
        AtualizacaoTipo dto;
        if (id != null) {
            Tipo item = tipoService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));
            dto = tipoMapper.toAtualizacaoDto(item);
        } else {
            dto = new AtualizacaoTipo(null, "");
        }
        model.addAttribute("item", dto);
        return "tipo/formulario"; // Usará um HTML genérico
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("item") @Valid AtualizacaoTipo dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("item", dto);
            return "tipo/formulario";
        }
        try {
            Tipo salvo = tipoService.salvarOuAtualizar(dto);
            String mensagem = dto.id() != null
                    ? "Tipo '" + salvo.getNome() + "' atualizado com sucesso!"
                    : "Tipo '" + salvo.getNome() + "' criado com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/tipo";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/tipo/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
        }
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deletar(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            tipoService.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message", "O item " + id + " foi apagado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Não foi possível apagar. Verifique se o item está em uso.");
        }
        return "redirect:/tipo";
    }
}