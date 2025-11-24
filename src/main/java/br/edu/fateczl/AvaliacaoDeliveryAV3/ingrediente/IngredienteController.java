package br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente;

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
@RequestMapping("/ingrediente")
public class IngredienteController {

    @Autowired private IngredienteService tipoService;

    @GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaIngredientes", tipoService.procurarTodos());
        return "ingrediente/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Integer id, Model model) {
        AtualizacaoIngrediente dto;
        if (id != null) {
        	Ingrediente item = tipoService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));
            dto = new AtualizacaoIngrediente(item.getId(), item.getNome(), item.getFormatoApresentacao());
            
        } else {
            dto = new AtualizacaoIngrediente(null, null, null);
        }
        model.addAttribute("ingrediente", dto);
        return "ingrediente/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("ingrediente") @Valid AtualizacaoIngrediente dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ingrediente", dto);
            return "ingrediente/formulario";
        }
        try {
        	Ingrediente salvo = tipoService.salvarOuAtualizar(dto);
            String mensagem = dto.id() != null
                    ? "Ingrediente '" + salvo.getNome() + "' atualizado com sucesso!"
                    : "Ingrediente '" + salvo.getNome() + "' criado com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/ingrediente";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/ingrediente/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
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
        return "redirect:/ingrediente";
    }
}