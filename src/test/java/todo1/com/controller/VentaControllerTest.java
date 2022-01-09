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

import todo1.com.domain.Cliente;
import todo1.com.domain.Venta;
import todo1.com.services.VentaService;

@WebMvcTest(VentaController.class)
class VentaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("ventaService")
	@MockBean
	private VentaService ventaService;

	@Test
	public void testFindAll() throws Exception {
		Cliente venta = new Cliente();
		venta.setCli_id(1L);
		List<Venta> listVenta = new ArrayList<>();
		listVenta.add(new Venta(1L, new Date(), true, new Date(), venta, null));
		listVenta.add(new Venta(2L, new Date(), true, new Date(), venta, null));
		listVenta.add(new Venta(3L, new Date(), true, new Date(), venta, null));
		Mockito.when(ventaService.findAll()).thenReturn(listVenta);
		String url = "/venta/";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(listVenta);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testPostEntity() throws JsonProcessingException, Exception {
		Cliente venta = new Cliente();
		venta.setCli_id(1L);
		Venta newVenta = new Venta(1L, new Date(), true, new Date(), venta, null);
		Venta savedVenta = new Venta(1L, new Date(), true, new Date(), venta, null);
		Mockito.when(ventaService.save(newVenta)).thenReturn(savedVenta);
		String url = "/venta/";
		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(newVenta))
				.with(csrf())).andExpect(status().isCreated())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Creado\"}"));
	}

	@Test
	void testFindById() throws Exception {
		Cliente venta = new Cliente();
		venta.setCli_id(1L);
		Long ventaId = 1L;
		Optional<Venta> findCliente = Optional.of(new Venta(1L, new Date(), true, new Date(), venta, null));
		Mockito.when(ventaService.findById(ventaId)).thenReturn(findCliente);
		String url = "/venta/" + ventaId;
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(findCliente);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testUpdate() throws Exception {
		Cliente venta = new Cliente();
		venta.setCli_id(1L);
		Venta exitsVenta = new Venta(1L, new Date(), true, new Date(), venta, null);
		Venta updateVenta = new Venta(1L, new Date(), true, new Date(), venta, null);
		Mockito.when(ventaService.update(exitsVenta)).thenReturn(updateVenta);
		String url = "/venta/";
		mockMvc.perform(put(url).contentType("application/json").content(objectMapper.writeValueAsString(exitsVenta))
				.with(csrf())).andExpect(status().isOk())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Actualizado\"}")).andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		Integer ventaId = 1;
		Mockito.doNothing().when(ventaService).delete((long) ventaId);
		String url = "/venta/" + ventaId;
		mockMvc.perform(delete(url)).andExpect(status().isOk());
		Mockito.verify(ventaService, times(1)).delete((long) ventaId);
	}

}
