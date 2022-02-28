package com.ian.projetointegradoianbs.resources;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ian.projetointegradoianbs.domain.Cidade;
import com.ian.projetointegradoianbs.domain.Colaborador;
import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.domain.Estado;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.domain.enuns.TipoEndereco;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(controllers = ColaboradorResource.class)
public class ColaboradorResourcesTeste {

        Colaborador mockColaboradorInsert() {
                LocalDate localDate = LocalDate.parse("2022-01-01");
                LocalDate localDate2 = LocalDate.parse("1990-01-01");
                Date dataVinculo = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date dataNascimento = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Estado estado = new Estado();
                estado.setId(1);
                estado.setNome("Minas Gerais");
                Cidade cidade = new Cidade();
                cidade.setEstado(estado);
                cidade.setId(1585);
                cidade.setNome("Varginha");
                Endereco endereco = new Endereco();
                endereco.setCidade(cidade);
                endereco.setId(3);
                endereco.setLogradouro("Rua Pimenta");
                endereco.setNumero(55);
                endereco.setCep("37582-003");
                endereco.setBairro("Perinhas");
                endereco.setComplemento("CASA");
                endereco.setCodigoIbge(null);
                endereco.setTipoEndereco(TipoEndereco.COMERCIAL);
                List<Endereco> enderecos = new ArrayList<Endereco>();
                enderecos.add(endereco);
                Usuario usuario = new Usuario();
                usuario.setId(null);
                usuario.setUsername("testejava");
                usuario.setEmail("ianbarbosasantos@gmail.com");
                usuario.setPermissoes(new ArrayList<>());
                Colaborador colaborador = new Colaborador();
                colaborador.setUsuario(usuario);
                colaborador.setEnderecos(enderecos);
                colaborador.setId(2L);
                colaborador.setNome("Teste Java Mock");
                colaborador.setCpf("12345678999");
                colaborador.setRg("MG12345678");
                colaborador.setDataVinculo(dataVinculo);
                colaborador.setDataNascimento(dataNascimento);
                return colaborador;
        }

        Colaborador mockColaboradorUpdate() {
                LocalDate localDate = LocalDate.parse("2022-01-01");
                LocalDate localDate2 = LocalDate.parse("1990-01-01");
                Date dataVinculo = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date dataNascimento = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Estado estado = new Estado();
                estado.setId(1);
                estado.setNome("Minas Gerais");
                Cidade cidade = new Cidade();
                cidade.setEstado(estado);
                cidade.setId(1585);
                cidade.setNome("Varginha");
                Endereco endereco = new Endereco();
                endereco.setCidade(cidade);
                endereco.setId(3);
                endereco.setLogradouro("Rua P");
                endereco.setNumero(55);
                endereco.setCep("37582-003");
                endereco.setBairro("Perinhas");
                endereco.setComplemento("CASA");
                endereco.setCodigoIbge(null);
                endereco.setTipoEndereco(TipoEndereco.COMERCIAL);
                List<Endereco> enderecos = new ArrayList<Endereco>();
                enderecos.add(endereco);
                Usuario usuario = new Usuario();
                usuario.setId(null);
                usuario.setUsername("testejava");
                usuario.setEmail("ianbarbosasantos@gmail.com");
                usuario.setPermissoes(new ArrayList<>());
                Colaborador colaborador = new Colaborador();
                colaborador.setUsuario(usuario);
                colaborador.setEnderecos(enderecos);
                colaborador.setId(2L);
                colaborador.setNome("Teste Java Mock");
                colaborador.setCpf("12345678999");
                colaborador.setRg("MG12345678");
                colaborador.setDataVinculo(dataVinculo);
                colaborador.setDataNascimento(dataNascimento);
                return colaborador;
        }

        @Autowired
        MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private ColaboradorResource colaboradorResource;

        @Test
        public void mustReturnSuccess_GetAllColaborador() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/colaborador/")
                                .header("Authorization",
                                                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2xhYm9yYWRvciIsInBlcm1pc3NvZXMiOltdLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjQ2MDY1Mzk0fQ.MjLIOXqkduVvQimOEQ7SLLaINBQ-YHhiHc74UtaPetTkDu4rtG09jYMRk32_yvYoXpJnf7KQTiw9omPpXsyKQg"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void mustReturnSuccess_GetOneColaborador() throws Exception {
                Colaborador colaborador = mockColaboradorInsert();

                mockMvc.perform(MockMvcRequestBuilders.get("/api/colaborador/{id}", colaborador.getId())
                                .header("Authorization",
                                                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2xhYm9yYWRvciIsInBlcm1pc3NvZXMiOltdLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjQ2MDY1Mzk0fQ.MjLIOXqkduVvQimOEQ7SLLaINBQ-YHhiHc74UtaPetTkDu4rtG09jYMRk32_yvYoXpJnf7KQTiw9omPpXsyKQg"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void mustReturnSuccess_InsertNewColaborador() throws JsonProcessingException, Exception {
                Colaborador colaborador = mockColaboradorInsert();

                mockMvc.perform(MockMvcRequestBuilders.post("/api/colaborador/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization",
                                                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2xhYm9yYWRvciIsInBlcm1pc3NvZXMiOltdLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjQ2MDY1Mzk0fQ.MjLIOXqkduVvQimOEQ7SLLaINBQ-YHhiHc74UtaPetTkDu4rtG09jYMRk32_yvYoXpJnf7KQTiw9omPpXsyKQg")
                                .content(objectMapper.writeValueAsString(colaborador)))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void mustReturnSuccess_UpdateColaborador() throws JsonProcessingException, Exception {
                Colaborador colaborador = mockColaboradorInsert();
                Colaborador colaboradorUpdate = mockColaboradorUpdate();

                mockMvc.perform(MockMvcRequestBuilders.put("/api/colaborador/{id}", colaborador.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization",
                                                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2xhYm9yYWRvciIsInBlcm1pc3NvZXMiOltdLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjQ2MDY1Mzk0fQ.MjLIOXqkduVvQimOEQ7SLLaINBQ-YHhiHc74UtaPetTkDu4rtG09jYMRk32_yvYoXpJnf7KQTiw9omPpXsyKQg")
                                .content(objectMapper.writeValueAsString(colaboradorUpdate)))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void mustReturnSuccess_DeleteColaborador() throws JsonProcessingException, Exception {
                Colaborador colaborador = mockColaboradorInsert();

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/colaborador/{id}", colaborador.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization",
                                                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2xhYm9yYWRvciIsInBlcm1pc3NvZXMiOltdLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjQ2MDY1Mzk0fQ.MjLIOXqkduVvQimOEQ7SLLaINBQ-YHhiHc74UtaPetTkDu4rtG09jYMRk32_yvYoXpJnf7KQTiw9omPpXsyKQg")
                                .content(objectMapper.writeValueAsString(colaborador)))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

}
