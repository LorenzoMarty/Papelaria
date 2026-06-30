package controller;

import jakarta.servlet.http.HttpSession;
import model.Produto;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.ProdutoService;

import java.util.List;

@Controller
public class ProdutoController {

    private final ProdutoService service = new ProdutoService();

    @GetMapping("/produto")
    public String listar(@RequestParam(required = false) String acao,
                         @RequestParam(required = false) Integer id,
                         Model model,
                         HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if ("editar".equals(acao)) {
            if (usuario == null || !usuario.isAdmin()) {
                return "redirect:/produto?msg=semPermissao";
            }
            if (id != null) {
                model.addAttribute("produto", service.buscarPorId(id));
            }
        }

        if ("excluir".equals(acao)) {
            if (usuario == null || !usuario.isAdmin()) {
                return "redirect:/produto?msg=semPermissao";
            }
            if (id != null) {
                try {
                    service.excluir(id);
                } catch (RuntimeException e) {
                    model.addAttribute("erro", "Produto possui vendas e não pode ser excluído.");
                    model.addAttribute("produtos", service.listar());
                    return "produtos";
                }
            }
            return "redirect:/produto?msg=excluido";
        }

        model.addAttribute("produtos", service.listar());
        return "produtos";
    }

    @PostMapping("/produto")
    public String salvar(@RequestParam(required = false) String id,
                         @RequestParam(defaultValue = "") String nome,
                         @RequestParam(defaultValue = "") String descricao,
                         @RequestParam(defaultValue = "") String preco,
                         @RequestParam(defaultValue = "") String quantidade,
                         Model model,
                         HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/produto?msg=semPermissao";
        }

        if (nome.trim().isEmpty()) {
            return recarregarComErro("Nome é obrigatório.", id, model);
        }
        if (descricao.trim().isEmpty()) {
            return recarregarComErro("Descrição é obrigatória.", id, model);
        }

        double precoVal;
        int qtdVal;
        try {
            precoVal = Double.parseDouble(preco.replace(',', '.'));
            qtdVal = Integer.parseInt(quantidade);
        } catch (NumberFormatException e) {
            return recarregarComErro("Preço e quantidade devem ser números válidos.", id, model);
        }

        if (precoVal <= 0) {
            return recarregarComErro("Preço deve ser maior que zero.", id, model);
        }
        if (qtdVal < 0) {
            return recarregarComErro("Quantidade não pode ser negativa.", id, model);
        }

        Produto p = new Produto(nome.trim(), descricao.trim(), precoVal, qtdVal);

        if (id != null && !id.trim().isEmpty()) {
            p.setId(Integer.parseInt(id));
            service.atualizar(p);
            return "redirect:/produto?msg=editado";
        } else {
            service.inserir(p);
            return "redirect:/produto?msg=salvo";
        }
    }

    private String recarregarComErro(String erro, String id, Model model) {
        if (id != null && !id.trim().isEmpty()) {
            model.addAttribute("produto", service.buscarPorId(Integer.parseInt(id)));
        }
        model.addAttribute("erro", erro);
        model.addAttribute("produtos", service.listar());
        return "produtos";
    }
}
