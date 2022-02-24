package com.ian.projetointegradoianbs.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;
import io.restassured.http.Header;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import com.ian.projetointegradoianbs.services.ColaboradorServices;
import com.ian.projetointegradoianbs.services.ProfissionalServices;
import com.ian.projetointegradoianbs.services.UsuarioServices;

@WebMvcTest(controllers = UsuarioResource.class)
public class UsuarioResourceTest {

    @Autowired
    private UsuarioResource usuarioResource;

    @MockBean
    private ColaboradorServices colaboradorServices;

    @MockBean
    private ProfissionalServices profissionalServices;

    @MockBean
    private UsuarioServices usuarioServices;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.usuarioResource);
    }

    @Test
    public void mustReturnSuccess_InsertUsuario() {
        Header header = new Header("Authorization",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpYW5icyIsInBlcm1pc3NvZXMiOltdLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjQ1Nzk1MzEyfQ.IqpxKbY3EhfUG_hxUqoN9HZV6pTa3wH7I9mDkiFcNqLxYWQaqnmneSjpJ6dISV9POrjCS9fq8KLZH6LmL8eFwg");
        given()
                // .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(header)
                .body("{\"username\": \"usuario\",\"password\": \"123456\", \"email\": \"usuario@gmail.com\",\"colaborador\": {\"nome\": \"Usuario Teste\",\"cpf\": \"11321804652\",\"rg\": \"MG17052193\",\"dataVinculo\": \"2022-01-01\",\"dataNascimento\": \"1991-12-19\",\"enderecos\": [{\"logradouro\": \"Rua Teste\",\"numero\": 55,\"cep\": \"39404-003\",\"bairro\": \"Sao Paulo\",\"complemento\": \"AP 102\",\"codigoIbge\": 123456,\"tipoEndereco\": 1,\"cidade\": {\"id\": 1,\"estado\": {\"id\": 1}}}]\"profissional\": null,\"permissoes\": null}")
                .when()
                .post("/api/usuario/")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
