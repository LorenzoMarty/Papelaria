package controller;

import jakarta.servlet.http.HttpSession;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import service.ProdutoService;
import service.VendaService;

@Controller
public class RelatorioController {

    private final VendaService vendaService = new VendaService();
    private final ProdutoService produtoService = new ProdutoService();

    @GetMapping("/relatorio")
    public String relatorio(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/produto?msg=semPermissao";
        }

        model.addAttribute("vendas", vendaService.listar());
        model.addAttribute("totalFaturado", vendaService.totalFaturado());
        model.addAttribute("totalVendas", vendaService.totalVendas());
        model.addAttribute("produtosEstoqueBaixo", produtoService.listarComEstoqueBaixo(5));

        return "relatorio";
    }
}
