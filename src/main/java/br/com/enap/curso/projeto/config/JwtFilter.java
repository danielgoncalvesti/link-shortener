package br.com.enap.curso.projeto.config;

import br.com.enap.curso.projeto.model.DTO.TokenDecoder;
import br.com.enap.curso.projeto.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    private static final String SECRET = "123";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return "/token".equals(path);
    }

    private String getUserIdByToken(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(token);

        String payload = decodedJWT.getPayload();

        byte[] payloadBytes = Base64.getDecoder().decode(payload.getBytes(StandardCharsets.UTF_8));

        TokenDecoder tokenDecoder = new Gson().fromJson(new String(payloadBytes), TokenDecoder.class);
        return tokenDecoder.getUserId();
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        // check the token here and bind the userId on request

        String userId =  getUserIdByToken(authorizationHeader);

        // set userId attribute on the request
        request.setAttribute("userId", userId);
        filterChain.doFilter(request, response);
    }

}
