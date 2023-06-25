package br.com.enap.curso.projeto.controller;

import br.com.enap.curso.projeto.errorHandling.ValidationError;
import br.com.enap.curso.projeto.model.DTO.TokenResponse;
import br.com.enap.curso.projeto.model.DTO.UserRequest;
import br.com.enap.curso.projeto.model.User;
import br.com.enap.curso.projeto.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/token")
public class TokenController {

    private static final String  SECRET= "123";
    @Autowired
    private UserService userService;

    LocalDateTime expiresAfter24h = LocalDateTime.now().plusHours(24);
    @PostMapping
    public TokenResponse generateToken(@RequestBody UserRequest userRequest) throws Exception {
        // check the username and pass
        User user = userService.getValidUserCredentials(userRequest.getUsername(), userRequest.getPass());
        var token = JWT.create()
                .withClaim("userId", user.getId())
                .withClaim("username", user.getUsername())
                .withExpiresAt(expiresAfter24h.toInstant(ZoneOffset.UTC))
                .sign(Algorithm.HMAC512(SECRET));

        return TokenResponse.builder()
                .token(token)
                .expiresAt(String.valueOf(expiresAfter24h)).build();
    }
}
