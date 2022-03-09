package com.ian.projetointegradoianbs.resources;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok().body(usuarioServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable UUID id) {
        Usuario objPessoa = usuarioServices.findById(id);
        return ResponseEntity.ok().body(objPessoa);
    }

    @PostMapping("/permissoes")
    public ResponseEntity<Permissoes> createRole(@RequestBody Permissoes permissoes) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServices.savePermissoes(permissoes));
    }

    @PostMapping("/permissoes/add")
    public ResponseEntity<?> addPermissoesByUsuario(@RequestBody AddPermissoesUsario permissoesUsario) {
        usuarioServices.savePermissoesByUsuario(permissoesUsario.getUsername(), permissoesUsario.getItem());
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
                userJsonObject.addProperty("id", usuario.getId().toString());
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