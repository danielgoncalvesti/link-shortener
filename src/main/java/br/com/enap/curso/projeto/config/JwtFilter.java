//package br.com.enap.curso.projeto.config;
//
//import br.com.enap.curso.projeto.service.UserService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private UserService userService;
//
//    private static final String SECRET = "123";
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request)
//            throws ServletException {
//        String path = request.getRequestURI();
//        return "/token".equals(path);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        final String token = request.getHeader("Authorization");
//        // check the token here and bind the userId on request
//
//        try {
//            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
//                    .build()
//                    .verify(token);
//
//            String payload = decodedJWT.getPayload();
//
//            byte[] payloadBytes = Base64.getDecoder().decode(payload.getBytes(StandardCharsets.UTF_8));
//
//            TokenDecoder tokenDecoder = new Gson().fromJson(new String(payloadBytes), TokenDecoder.class);
//            request.setAttribute("X-User-Id", tokenDecoder.getUserId());
//            filterChain.doFilter(request, response);
//        } catch (Exception e ){
//            ResponseMessage responseMessage = new ResponseMessage("Invalid token");
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getWriter().write(new Gson().toJson(responseMessage));
//            response.setHeader("Content-Type", "application/json");
//        }
//
//    }
//
//}
