package br.edu.fateczl.AvaliacaoDeliveryAV3.tipo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@RequestMapping("/tipo")
public class TipoController {

    @Autowired private TipoService tipoService;
    @Autowired private DataSource ds;
    
    @GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaTipos", tipoService.procurarTodos());
        return "tipo/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Integer id, Model model) {
        AtualizacaoTipo dto;
        if (id != null) {
            Tipo item = tipoService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));
            dto = new AtualizacaoTipo(item.getId(), item.getNome());
        } else {
            dto = new AtualizacaoTipo(null, "");
        }
        model.addAttribute("tipo", dto);
        return "tipo/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("tipo") @Valid AtualizacaoTipo dto,
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
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("/relatorio/{nome}")
    public ResponseEntity gerarRelatorio(@PathVariable("nome") String nome, RedirectAttributes redirectAttributes) {
    	String erro = "";
    	Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("tipo", nome);
		
		Connection conn = DataSourceUtils.getConnection(ds);
		
		byte[] bytes = null;
		InputStreamResource resources = null;
		HttpStatus status = null;
		HttpHeaders header = new HttpHeaders();
		
		try {
			String path = "classpath:reports/relatorio01.jasper";
			File arquivo = ResourceUtils.getFile(path);
			JasperReport report = (JasperReport) JRLoader
					.loadObjectFromFile(
							arquivo.getAbsolutePath()
					);
			bytes = JasperRunManager.runReportToPdf(report, reportParams, conn);
		} catch(Exception e) {
			erro = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} finally {
			if (erro.equals("")) {
				ByteArrayInputStream stream = 
						new ByteArrayInputStream(bytes);
				resources = new InputStreamResource(stream);
				status = HttpStatus.OK;
				header.setContentLength(bytes.length);
				header.setContentType(MediaType.APPLICATION_PDF);
			}
		}
		
		return new ResponseEntity(resources, header, status);
    	
    }
}