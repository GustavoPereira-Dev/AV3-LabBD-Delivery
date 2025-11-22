package br.edu.fateczl.AvaliacaoDeliveryAV3.porcao;

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
@RequestMapping("/porcao")
public class PorcaoController {

    @Autowired private PorcaoService tipoService;
    @Autowired private PorcaoMapper tipoMapper;

    @GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaPorcoes", tipoService.procurarTodos());
        return "porcao/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Integer id, Model model) {
        AtualizacaoPorcao dto;
        if (id != null) {
        	Porcao item = tipoService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));
            dto = tipoMapper.toAtualizacaoDto(item);
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
        	Porcao salvo = tipoService.salvarOuAtualizar(dto);
            String mensagem = dto.id() != null
                    ? "Tipo '" + salvo.getTamanho() + "' atualizado com sucesso!"
                    : "Tipo '" + salvo.getTamanho() + "' criado com sucesso!";
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
            tipoService.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message", "O item " + id + " foi apagado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Não foi possível apagar. Verifique se o item está em uso.");
        }
        return "redirect:/porcao";
    }
}