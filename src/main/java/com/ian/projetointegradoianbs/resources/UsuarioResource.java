package com.ian.projetointegradoianbs.resources;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.ian.projetointegradoianbs.domain.Permissoes;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.security.JWTAuthFilter;
import com.ian.projetointegradoianbs.security.JWTValidateFilter;
import com.ian.projetointegradoianbs.services.UsuarioServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok().body(usuarioServices.listAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> find(@PathVariable Long id) {
        Usuario objPessoa = usuarioServices.find(id);
        return ResponseEntity.ok().body(objPessoa);
    }

    @PostMapping("/permissoes")
    public ResponseEntity<Permissoes> createRole(@RequestBody Permissoes permissoes) {
        URI uri = URI.create(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/usuario/permissoes").toUriString());
        return ResponseEntity.created(uri).body(usuarioServices.insertPermissoes(permissoes));
    }

    @PostMapping("/permissoes/add")
    public ResponseEntity<?> addPermissoesUsuario(@RequestBody AddPermissoesUsario form) {
        usuarioServices.addPermissoesUsuario(form.getUsername(), form.getItem());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(JWTValidateFilter.HEADER_ATTR);
        if (authHeader != null && authHeader.startsWith(JWTValidateFilter.PREFIX)) {
            try {
                String refresh_token = authHeader.substring(JWTValidateFilter.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC512(JWTAuthFilter.TOKEN_PASSWORD.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Usuario usuario = (Usuario) usuarioServices.loadUserByUsername(username);
                String token = JWT.create()
                        .withSubject(usuario.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTAuthFilter.TOKEN_EXPIRACAO))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("permissoes",
                                usuario.getPermissoes().stream().map(Permissoes::getItem)
                                        .collect(Collectors.toList()))
                        .sign(Algorithm.HMAC512(JWTAuthFilter.TOKEN_PASSWORD.getBytes()));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("token", token);
                jsonObject.addProperty("refresh_token", refresh_token);

                JsonObject userJsonObject = new JsonObject();
                userJsonObject.addProperty("id", usuario.getId());
                userJsonObject.addProperty("nome", usuario.getUsername());
                userJsonObject.addProperty("email", usuario.getEmail());

                jsonObject.add("usuario", userJsonObject);

                response.getWriter().write(jsonObject.toString());
                response.getWriter().flush();

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(403);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("error", e.getMessage());
                response.getWriter().write(jsonObject.toString());
                response.getWriter().flush();
            }

        } else {
            throw new RuntimeException("Refresh token não está presente");
        }
    }

}

@Data
class AddPermissoesUsario {
    private String username;
    private String item;
}