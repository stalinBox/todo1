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
import java.util.Date;
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
import todo1.com.domain.Proveedor;
import todo1.com.services.CompraService;

@WebMvcTest(CompraController.class)
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
		Proveedor proveedor = new Proveedor();
		proveedor.setProve_id(1L);
		List<Compra> listCompra = new ArrayList<>();
		listCompra.add(new Compra(1L, new Date(), true, new Date(), proveedor, null));
		listCompra.add(new Compra(2L, new Date(), true, new Date(), proveedor, null));
		listCompra.add(new Compra(3L, new Date(), true, new Date(), proveedor, null));
		Mockito.when(compraService.findAll()).thenReturn(listCompra);
		String url = "/compra/";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(listCompra);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testPostEntity() throws JsonProcessingException, Exception {
		Proveedor proveedor = new Proveedor();
		proveedor.setProve_id(1L);
		Compra newCompra = new Compra(1L, new Date(), true, new Date(), proveedor, null);
		Compra savedCompra = new Compra(1L, new Date(), false, new Date(), proveedor, null);

		Mockito.when(compraService.save(newCompra)).thenReturn(savedCompra);
		String url = "/compra/";
		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(newCompra))
				.with(csrf())).andExpect(status().isCreated())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Creado\"}"));
	}

	@Test
	void testFindById() throws Exception {
		Proveedor proveedor = new Proveedor();
		proveedor.setProve_id(1L);
		Long clienteId = 1L;
		Optional<Compra> findCliente = Optional.of(new Compra(1L, new Date(), false, new Date(), proveedor, null));
		Mockito.when(compraService.findById(clienteId)).thenReturn(findCliente);
		String url = "/compra/" + clienteId;
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(findCliente);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testUpdate() throws Exception {
		Proveedor proveedor = new Proveedor();
		proveedor.setProve_id(1L);
		Compra exitCliente = new Compra(1L, new Date(), true, new Date(), proveedor, null);
		Compra updateCliente = new Compra(1L, new Date(), true, new Date(), proveedor, null);
		Mockito.when(compraService.update(exitCliente)).thenReturn(updateCliente);
		String url = "/compra/";
		mockMvc.perform(put(url).contentType("application/json").content(objectMapper.writeValueAsString(exitCliente))
				.with(csrf())).andExpect(status().isOk())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Actualizado\"}")).andDo(print());
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
