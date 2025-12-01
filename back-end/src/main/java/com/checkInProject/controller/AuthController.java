package grupoHeitorECaio.checkInProject.controller;

import grupoHeitorECaio.checkInProject.model.Usuario; // Certifique-se de ter a classe Usuario criada
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth") // Bate com a pasta "auth" do service
@CrossOrigin(origins = "*") // Permite que o React acesse sem bloqueio
public class AuthController {

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        // aqui seria a validação de usuário no futuro.
        // Por enquanto, devolver um usuário teste para o front funcionar.

        Map<String, Object> response = new HashMap<>();
        response.put("id", 1);
        response.put("nome", "Usuário Teste");
        response.put("email", loginData.get("email"));
        response.put("token", "token-fake-jwt-123456");

        return response;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Usuario usuario) {
        // aqui salvaria no HashMap de usuários
        return Map.of("message", "Usuário criado com sucesso!");
    }
}