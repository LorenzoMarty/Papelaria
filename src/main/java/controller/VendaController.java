package controller;

import jakarta.servlet.http.HttpSession;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.ProdutoService;
import service.VendaService;

@Controller
public class VendaController {

    private final VendaService vendaService = new VendaService();
    private final ProdutoService produtoService = new ProdutoService();

    @GetMapping("/venda")
    public String listar(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/?msg=loginParaComprar";
        }

        model.addAttribute("produtos", produtoService.listar());
        model.addAttribute("vendas", vendaService.listar());
        return "vendas";
    }

    @PostMapping("/venda")
    public String registrar(@RequestParam(defaultValue = "") String produtoId,
                            @RequestParam(defaultValue = "") String quantidade,
                            Model model,
                            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/?msg=loginParaComprar";
        }

        int qtd;
        int pId;
        try {
            qtd = Integer.parseInt(quantidade);
            pId = Integer.parseInt(produtoId);
        } catch (NumberFormatException e) {
            model.addAttribute("erro", "Selecione um produto e informe uma quantidade válida.");
            model.addAttribute("produtos", produtoService.listar());
            model.addAttribute("vendas", vendaService.listar());
            return "vendas";
        }

        String erro = vendaService.registrarVenda(pId, usuario.getId(), qtd);
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("produtos", produtoService.listar());
            model.addAttribute("vendas", vendaService.listar());
            return "vendas";
        }

        return "redirect:/venda?msg=registrado";/
    }
}
