package br.edu.fateczl.AvaliacaoDeliveryAV3.cliente;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaClientes", clienteService.procurarTodos());
        return "cliente/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) String id, Model model) {
        AtualizacaoCliente dto;
        if (id != null) {
            // Edição
            Cliente cliente = clienteService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
            dto = clienteMapper.toAtualizacaoDto(cliente);
        } else {
            // Criação
            dto = new AtualizacaoCliente(null, "", "", "", 0, "", "");
        }
        model.addAttribute("cliente", dto);
        return "cliente/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("cliente") @Valid AtualizacaoCliente dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        if (result.hasErrors()) {
            // Se o CPF for nulo/inválido na criação, o BindingResult pega
            // Se o ID for de edição e tiver erros, recarrega
            model.addAttribute("cliente", dto);
            return "cliente/formulario";
        }

        try {
            // Tenta salvar (se o CPF já existir, atualiza; senão, cria)
            Cliente clienteSalvo = clienteService.salvarOuAtualizar(dto);
            
            // Verifica se o cliente já existia (pela busca no service) para definir a mensagem
            boolean eraEdicao = clienteService.procurarPorId(dto.cpf()).isPresent();

            String mensagem = eraEdicao
                    ? "Cliente '" + clienteSalvo.getNome() + "' atualizado com sucesso!"
                    : "Cliente '" + clienteSalvo.getNome() + "' criado com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/cliente";
            
        } catch (Exception e) {
            // Captura outras exceções (ex: banco de dados)
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar cliente: " + e.getMessage());
            model.addAttribute("cliente", dto);
            return "cliente/formulario";
        }
    }


    @GetMapping("/delete/{id}")
    @Transactional
    public String deletarCliente(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message", "O cliente com CPF " + id + " foi apagado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Não foi possível apagar o cliente. Verifique se ele possui pedidos associados.");
        }
        return "redirect:/cliente";
    }
}