package br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
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
    @Autowired private DataSource ds;

    @GetMapping
    public String gerenciar(Model model) {
        model.addAttribute("listaFichas", service.procurarTodos());
        
        model.addAttribute("ficha", new AtualizacaoPratoIngrediente(null, null, null));
        
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
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("/relatorio")
    public ResponseEntity gerarRelatorio(@RequestParam("data") LocalDate data, RedirectAttributes redirectAttributes) {
    	String erro = "";
    	Map<String, Object> reportParams = new HashMap<>();
    	
		reportParams.put("data", data.toString());
		
		Connection conn = DataSourceUtils.getConnection(ds);
		
		byte[] bytes = null;
		InputStreamResource resources = null;
		HttpStatus status = null;
		HttpHeaders header = new HttpHeaders();
		
		try {
			String path = "classpath:reports/relatorio03.jasper";
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