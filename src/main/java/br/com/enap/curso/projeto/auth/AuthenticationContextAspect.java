package br.com.enap.curso.projeto.auth;

import br.com.enap.curso.projeto.errorHandling.ValidationError;
import br.com.enap.curso.projeto.model.DTO.AuthenticatedUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Aspect
@Component
public class AuthenticationContextAspect {

    private static final String SECRET = "123";

    @Before(value = "@annotation(authenticationContext)", argNames = "authenticationContext")
    public void preHandle(AuthenticationContext authenticationContext) throws Exception {
        HttpServletRequest request =
                ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                        .getRequest();
        var token = request.getHeader("Authorization");

        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                    .build()
                    .verify(token);

            String payload = decodedJWT.getPayload();

            byte[] payloadBytes = Base64.getDecoder().decode(payload.getBytes(StandardCharsets.UTF_8));

            AuthenticatedUser authenticatedUser = new Gson().fromJson(new String(payloadBytes), AuthenticatedUser.class);

            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            requestAttributes.setAttribute("authenticatedUser", authenticatedUser, RequestAttributes.SCOPE_REQUEST);
        } catch (Exception e){
            throw new ValidationError("Invalid token", HttpStatus.UNAUTHORIZED.value());
        }

    }
}
