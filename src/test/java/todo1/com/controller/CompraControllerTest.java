package todo1.com.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import todo1.com.domain.Compra;
import todo1.com.services.ClienteService;
import todo1.com.services.CompraService;

@WebMvcTest(ClienteController.class)
class CompraControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("compraService")
	@MockBean
	private CompraService compraService;

	@Test
	public void testFindAll() throws Exception {
		List<Compra> listCompra = new ArrayList<>();
		listCompra.add(new Compra(1L, "cliente1", "apellido1", "Direccion1", "0998525877", null, null, null));
		listCompra.add(new Compra(2L, "cliente2", "apellido2", "Direccion2", "0998525877", null, null, null));
		listCompra.add(new Compra(3L, "cliente3", "apellido3", "Direccion3", "0998525877", null, null, null));
		Mockito.when(compraService.findAll()).thenReturn(listCompra);
		String url = "/compra/";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(listCompra);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testPostEntity() throws JsonProcessingException, Exception {
		Compra newCompra = new Compra(101L, "cliente1", "apellido1", "Direccion1", "0998525877", true, null, null);
		Compra savedCompra = new Compra(101L, "cliente1", "apellido1", "Direccion1", "0998525877", true, null, null);
		Mockito.when(compraService.save(newCompra)).thenReturn(savedCompra);
		String url = "/compra/";
		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(newCompra))
				.with(csrf())).andExpect(status().isCreated())
				.andExpect(content().string("{\"id\":" + 101L + ",\"estado\":\"Creado\"}"));
	}

	@Test
	void testFindById() throws Exception {
		Long clienteId = 1L;
		Optional<Compra> findCliente = Optional
				.of(new Compra(1L, "cliente1", "apellido1", "Direccion1", "0998525877", null, null, null));
		Mockito.when(compraService.findById(clienteId)).thenReturn(findCliente);
		String url = "/compra/" + clienteId;
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(findCliente);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testUpdate() throws Exception {
		Compra exitCliente = new Compra(122L, "cliente1", "apellido1", "Direccion1", "0998525877", null, null, null);
		Compra updateCliente = new Compra(122L, "clienxx", "apel", "Direc", "099852", null, null, null);
		Mockito.when(compraService.update(exitCliente)).thenReturn(updateCliente);
		String url = "/compra/";
		mockMvc.perform(put(url).contentType("application/json").content(objectMapper.writeValueAsString(exitCliente))
				.with(csrf())).andExpect(status().isOk())
				.andExpect(content().string("{\"id\":" + 122L + ",\"estado\":\"Actualizado\"}")).andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		Integer clienteId = 1;
		Mockito.doNothing().when(compraService).delete((long) clienteId);
		String url = "/compra/" + clienteId;
		mockMvc.perform(delete(url)).andExpect(status().isOk());
		Mockito.verify(compraService, times(1)).delete((long) clienteId);
	}

}
