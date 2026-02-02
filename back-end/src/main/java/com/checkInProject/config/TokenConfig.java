package com.checkInProject.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkInProject.model.ETipoUsuario;
import com.checkInProject.model.Usuario;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class TokenConfig {

    private String secret = "secret";

    public String generateToken(Usuario user) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        List<String> rolesAsString = user.getRoles().stream()
                .map(ETipoUsuario::name)
                .toList();


        return JWT.create()
                .withClaim("userId", user.getId())
                .withClaim("roles", rolesAsString)
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decode = JWT.require(algorithm)
                    .build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("userId").asLong())
                    .email(decode.getSubject())
                    .roles(decode.getClaim("roles").asList(String.class))
                    .build());
        }
        catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }
}