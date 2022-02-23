package com.ian.projetointegradoianbs.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.ian.projetointegradoianbs.domain.Usuario;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRACAO = 86_000_000;

    public static final String TOKEN_PASSWORD = "fb284ca8-91fd-11ec-b909-0242ac120002";

    private final AuthenticationManager authenticationManager;

    public JWTAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (super.requiresAuthentication(request, response)) {
            if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
                return false;
            }
            Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
            if (existingAuth == null || !existingAuth.isAuthenticated()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {

            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(),
                    Usuario.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(),
                    usuario.getPassword(), Collections.emptyList()));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar o usuário");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {

        Usuario usuarioData = (Usuario) authentication.getPrincipal();

        String token = JWT.create()
                .withSubject(usuarioData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                .withIssuer(request.getRequestURI().toString())
                .withClaim("permissoes",
                        usuarioData.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD.getBytes()));

        String refresh_token = JWT.create()
                .withSubject(usuarioData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                .withIssuer(request.getRequestURI().toString())
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("token", token);
        jsonObject.addProperty("refresh_token", refresh_token);

        JsonObject userJsonObject = new JsonObject();
        userJsonObject.addProperty("id", usuarioData.getId());
        userJsonObject.addProperty("nome", usuarioData.getUsername());
        userJsonObject.addProperty("email", usuarioData.getEmail());

        jsonObject.add("usuario", userJsonObject);

        response.getWriter().write(jsonObject.toString());
        response.getWriter().flush();
    }

}
