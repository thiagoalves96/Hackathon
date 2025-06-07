package com.healthIo.health.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    // Exibe a página de login
    @GetMapping("/login")
    public String exibirLogin() {
        return "login"; // Retorna a view "login" (sem a extensão .html)
    }

    // Processa o login (POST)
    @PostMapping("/login")
    public String autenticar(@RequestParam String username,
            @RequestParam String password,
            Model model) {
        if ("admin".equals(username) && "admin".equals(password)) {
            // Se as credenciais forem válidas, redireciona para a página do perfil
            return "redirect:/perfil";
        } else {
            // Se as credenciais forem inválidas, retorna à página de login com erro
            model.addAttribute("erro", "Usuário ou senha inválidos");
            return "login"; // Retorna para a view "login" (sem a extensão .html)
        }
    }

    // Exibe a página de perfil (após login bem-sucedido)
    @GetMapping("/perfil")
    public String mostrarPerfil() {
        return "perfil"; // Retorna a view "perfil" (sem a extensão .html)
    }
}
