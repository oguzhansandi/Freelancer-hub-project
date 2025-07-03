package com.freelance.config;

import com.freelance.jwt.JWTService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JWTService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        URI uri = request.getURI();
        String query = uri.getQuery();

        if (query != null && query.startsWith("token=")) {
            String token = Arrays.stream(query.split("&"))
                    .filter(p -> p.startsWith("token="))
                    .map(p -> p.substring("token=".length()))
                    .findFirst()
                    .orElse(null);

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }


            try {
                Claims claims = jwtService.getClaims(token);
                String username = jwtService.getUsernameByToken(token);
                String role = claims.get("role", String.class);

                if (username != null && jwtService.isTokenValid(token)) {
                    attributes.put("username", username);
                    attributes.put("role", role);
                    return true;
                }
            } catch (Exception e) {
                System.out.println("JWT token hatası: " + e.getMessage());
            }
        }

        return false;
    }
    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
    }
}
