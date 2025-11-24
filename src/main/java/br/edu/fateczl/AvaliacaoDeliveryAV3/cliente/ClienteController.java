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

    @GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaClientes", clienteService.procurarTodos());
        return "cliente/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) String id, Model model) {
        AtualizacaoCliente dto;
        if (id != null) {
            Cliente cliente = clienteService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
            dto = new AtualizacaoCliente(cliente.getCpf(), 
            							cliente.getNome(), 
            							cliente.getTelefone(), 
            							cliente.getEndereco(), 
            							cliente.getNumero(), 
            							cliente.getCep(), 
            							cliente.getPontoReferencia());
            		
            /*        @NotBlank(message = "CPF é obrigatório")
        @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
        String cpf,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,
        
        @NotBlank(message = "Endereço é obrigatório")
        String endereco,

        @NotNull(message = "Número é obrigatório")
        @Positive(message = "Número deve ser positivo")
        Integer numero,

        @NotBlank(message = "CEP é obrigatório")
        @Size(min = 8, max = 8, message = "CEP deve ter 8 caracteres")
        String cep,

        String pontoReferencia
             * 
             * */
            
        } else {
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
            model.addAttribute("cliente", dto);
            return "cliente/formulario";
        }

        try {
            Cliente clienteSalvo = clienteService.salvarOuAtualizar(dto);
            
            boolean eraEdicao = clienteService.procurarPorId(dto.cpf()).isPresent();

            String mensagem = eraEdicao
                    ? "Cliente '" + clienteSalvo.getNome() + "' atualizado com sucesso!"
                    : "Cliente '" + clienteSalvo.getNome() + "' criado com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/cliente";
            
        } catch (Exception e) {
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