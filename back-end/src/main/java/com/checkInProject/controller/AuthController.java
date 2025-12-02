package com.checkInProject.controller;

import com.checkInProject.model.Usuario;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        // Simulação de login
        Map<String, Object> response = new HashMap<>();
        response.put("id", 1);
        response.put("nome", "Usuário Teste");
        response.put("email", loginData.get("email"));
        response.put("token", "token-fake-jwt-123456");

        return response;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Usuario usuario) {
        return Map.of("message", "Usuário criado com sucesso!");
    }
}