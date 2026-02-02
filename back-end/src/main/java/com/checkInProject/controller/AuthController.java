package com.checkInProject.controller;

import com.checkInProject.config.TokenConfig;
import com.checkInProject.dto.request.LoginRequest;
import com.checkInProject.dto.request.RegisterUsuarioRequest;
import com.checkInProject.dto.response.LoginResponse;
import com.checkInProject.dto.response.RegisterUsuarioResponse;
import com.checkInProject.model.ETipoUsuario;
import com.checkInProject.model.Usuario;
import com.checkInProject.repository.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")

public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        Usuario user = (Usuario) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUsuarioResponse> register(@Valid @RequestBody RegisterUsuarioRequest request) {
        Usuario newUser = new Usuario();
        newUser.setSenha(passwordEncoder.encode(request.password()));
        newUser.setEmail(request.email());
        newUser.setNome(request.name());
        newUser.setRoles(Set.of(ETipoUsuario.PARTICIPANTE));

        usuarioRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUsuarioResponse(newUser.getNome(), newUser.getEmail()));
    }

}