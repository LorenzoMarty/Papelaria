package controller;

import jakarta.servlet.http.HttpSession;
import model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.LoginService;

@Controller
public class LoginController {

    private final LoginService service = new LoginService();

    @GetMapping("/")
    public String home() {
        return "redirect:/produto";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session != null && session.getAttribute("usuario") != null) {
            return "redirect:/produto";
        }
        return "forward:/index.jsp";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session,
                        Model model) {

        if (email == null || email.trim().isEmpty() || senha == null || senha.isEmpty()) {
            model.addAttribute("erro", "Informe e-mail e senha.");
            return "forward:/index.jsp";
        }

        Usuario usuario;
        try {
            usuario = service.autenticar(email.trim(), senha);
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Erro ao conectar ao banco de dados. Verifique usuário e senha do PostgreSQL.");
            return "forward:/index.jsp";
        }

        if (usuario != null) {
            session.setAttribute("usuario", usuario);
            return "redirect:/produto";
        } else {
            model.addAttribute("erro", "Usuário ou senha inválidos.");
            return "forward:/index.jsp";
        }
    }
}
