package com.ian.projetointegradoianbs.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Cidade;
import com.ian.projetointegradoianbs.domain.Colaborador;
import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.domain.Estado;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.repository.ColaboradorRepository;

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(controllers = ColaboradorServices.class)
public class ColaboradorServicesTeste {

    public Colaborador mockColaborador() {
        Colaborador colaborador = Mockito.mock(Colaborador.class);
        Estado estado = Mockito.mock(Estado.class);
        Cidade cidade = Mockito.mock(Cidade.class);
        cidade.setEstado(estado);
        Endereco endereco = Mockito.mock(Endereco.class);
        List<Endereco> enderecos = new ArrayList<Endereco>();
        enderecos.add(endereco);
        Usuario usuario = Mockito.mock(Usuario.class);
        colaborador.setUsuario(usuario);
        colaborador.setEnderecos(enderecos);
        return colaborador;
    }

    @Autowired
    private ColaboradorServices colaboradorServices;

    @MockBean
    private ColaboradorRepository colaboradorRepository;

    @MockBean
    private UsuarioServices usuarioServices;

    @MockBean
    private EnderecoServices enderecoServices;

    @MockBean
    private PasswordEncoder usuarioEncoder;

    @BeforeEach
    public void setup() {
        // Colaborador colaborador = mockColaborador();

        // // when(mock.isOk()).thenReturn(true);
        // // when(mock.isOk()).thenThrow(exception);
        // // doThrow(exception).when(mock).someVoidMethod();

        // Mockito.when(colaboradorRepository.findById(anyLong())).thenReturn(Optional.of(colaborador));

        // Mockito.doNothing().when(colaboradorRepository).delete(colaborador);
    }

    @Test
    public void mustReturnSuccess_InsertNewColaborador() {
        Colaborador colaborador = Mockito.mock(Colaborador.class);
        Endereco endereco = Mockito.mock(Endereco.class);
        List<Endereco> enderecos = new ArrayList<Endereco>();
        enderecos.add(endereco);
        Usuario usuario = Mockito.mock(Usuario.class);

        Mockito.when(colaborador.getEnderecos()).thenReturn(enderecos);
        Mockito.when(colaborador.getUsuario()).thenReturn(usuario);

        Colaborador colaboradorNovo = colaboradorServices.insertColaborador(colaborador);
        Assertions.assertEquals(colaboradorNovo, null);

        Mockito.verify(colaboradorRepository, Mockito.times(1)).save(any(Colaborador.class));
    }

    @Test
    public void mustReturnSuccess_FindColaborador() {

        Long colaboradorId = Long.MAX_VALUE;
        Colaborador colaborador = Mockito.mock(Colaborador.class);

        Optional<Colaborador> optional = Optional.of(colaborador);
        Mockito.when(colaboradorRepository.findById(colaboradorId)).thenReturn(optional);

        Colaborador resultado = colaboradorServices.findColaborador(colaboradorId);

        Assertions.assertEquals(optional.get(), resultado);
        Mockito.verify(colaboradorRepository, Mockito.times(1)).findById(anyLong());

    }

    @Test
    public void mustReturnSuccess_UpdateColaborador() {
        Long colaboradorId = Long.MAX_VALUE;

        Colaborador colaborador = Mockito.mock(Colaborador.class);
        Endereco endereco = Mockito.mock(Endereco.class);
        List<Endereco> enderecos = new ArrayList<Endereco>();
        enderecos.add(endereco);
        Usuario usuario = Mockito.mock(Usuario.class);

        Mockito.when(colaborador.getEnderecos()).thenReturn(enderecos);
        Mockito.when(colaborador.getUsuario()).thenReturn(usuario);
        Mockito.when(colaborador.getId()).thenReturn(colaboradorId);

        Optional<Colaborador> optional = Optional.of(colaborador);
        Mockito.when(colaboradorRepository.findById(colaboradorId)).thenReturn(optional);

        Colaborador resultado = colaboradorServices.updateColaborador(colaborador);

        Assertions.assertEquals(resultado, null);

        Mockito.verify(colaboradorRepository, Mockito.times(1)).findById(anyLong());
        Mockito.verify(colaboradorRepository, Mockito.times(1)).save(any(Colaborador.class));
    }

    @Test
    public void mustReturnSuccess_DeleteColaborador() {
        Long colaboradorId = Long.MAX_VALUE;

        Colaborador colaborador = Mockito.mock(Colaborador.class);
        Endereco endereco = Mockito.mock(Endereco.class);
        List<Endereco> enderecos = new ArrayList<Endereco>();
        enderecos.add(endereco);
        Usuario usuario = Mockito.mock(Usuario.class);

        Mockito.when(colaborador.getEnderecos()).thenReturn(enderecos);
        Mockito.when(colaborador.getUsuario()).thenReturn(usuario);
        Mockito.when(colaborador.getId()).thenReturn(colaboradorId);

        Optional<Colaborador> optional = Optional.of(colaborador);
        Mockito.when(colaboradorRepository.findById(colaboradorId)).thenReturn(optional);

        colaboradorServices.deleteColaborador(colaborador);

        Mockito.verify(colaboradorRepository, Mockito.times(1)).deleteById(anyLong());
    }

}
