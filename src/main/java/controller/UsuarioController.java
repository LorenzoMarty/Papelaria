package controller;

import jakarta.servlet.http.HttpSession;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.UsuarioService;

@Controller
public class UsuarioController {

    private final UsuarioService service = new UsuarioService();

    @GetMapping("/usuario")
    public String listar(@RequestParam(required = false) String acao,
                         @RequestParam(required = false) Integer id,
                         Model model,
                         HttpSession session) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
        if (usuarioLogado == null || !usuarioLogado.isAdmin()) {
            return "redirect:/produto?msg=semPermissao";
        }

        if ("editar".equals(acao) && id != null) {
            model.addAttribute("usuarioEditado", service.buscarPorId(id));
        }

        if ("excluir".equals(acao) && id != null) {
            if (usuarioLogado.getId() == id) {
                return "redirect:/usuario?msg=proprio";
            }
            service.excluir(id);
            return "redirect:/usuario?msg=excluido";
        }

        model.addAttribute("usuarios", service.listar());
        return "usuarios";
    }

    @PostMapping("/usuario")
    public String salvar(@RequestParam(required = false) String id,
                         @RequestParam(defaultValue = "") String nome,
                         @RequestParam(defaultValue = "") String email,
                         @RequestParam(defaultValue = "") String senha,
                         @RequestParam(defaultValue = "true") String ativo,
                         @RequestParam(defaultValue = "false") String admin,
                         @RequestParam(required = false) String origem,
                         Model model,
                         HttpSession session) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
        boolean adminLogado = usuarioLogado != null && usuarioLogado.isAdmin();
        boolean cadastroAdmin = adminLogado && "admin".equals(origem);

        if (nome.trim().isEmpty()) {
            return erroUsuario("Nome é obrigatório.", id, cadastroAdmin, model);
        }
        if (email.trim().isEmpty() || !email.contains("@")) {
            return erroUsuario("Informe um e-mail válido.", id, cadastroAdmin, model);
        }
        if (id != null && !id.trim().isEmpty()) {
            if (!adminLogado) {
                return "redirect:/produto?msg=semPermissao";
            }
            String senhaFinal;
            if (senha.trim().isEmpty()) {
                senhaFinal = service.buscarPorId(Integer.parseInt(id)).getSenha();
            } else if (senha.length() < 4) {
                return erroUsuario("Senha deve ter no mínimo 4 caracteres.", id, cadastroAdmin, model);
            } else {
                senhaFinal = senha;
            }
            boolean ativoVal = Boolean.parseBoolean(ativo);
            boolean adminVal = Boolean.parseBoolean(admin);
            Usuario u = new Usuario(Integer.parseInt(id), nome.trim(), email.trim(), senhaFinal, ativoVal, adminVal);
            service.atualizar(u);
            return "redirect:/usuario?msg=editado";
        }

        if (senha.trim().isEmpty() || senha.length() < 4) {
            return erroUsuario("Senha deve ter no mínimo 4 caracteres.", id, cadastroAdmin, model);
        }

        if (service.emailExiste(email.trim())) {
            return erroUsuario("E-mail já cadastrado.", id, cadastroAdmin, model);
        }

        boolean ativoVal = cadastroAdmin ? Boolean.parseBoolean(ativo) : true;
        boolean adminVal = cadastroAdmin && Boolean.parseBoolean(admin);
        Usuario u = new Usuario(nome.trim(), email.trim(), senha, ativoVal, adminVal);

        boolean sucesso = service.inserir(u);

        if (sucesso) {
            return cadastroAdmin ? "redirect:/usuario?msg=salvo" : "redirect:/?msg=cadastrado";
        } else {
            return erroUsuario("Erro ao cadastrar usuário.", id, cadastroAdmin, model);
        }
    }

    private String erroUsuario(String erro, String id, boolean cadastroAdmin, Model model) {
        model.addAttribute("erro", erro);
        if (cadastroAdmin) {
            if (id != null && !id.trim().isEmpty()) {
                model.addAttribute("usuarioEditado", service.buscarPorId(Integer.parseInt(id)));
            }
            model.addAttribute("usuarios", service.listar());
            return "usuarios";
        }
        return "cadastro";
    }
}
