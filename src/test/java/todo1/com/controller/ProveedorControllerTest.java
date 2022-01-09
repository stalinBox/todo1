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

import todo1.com.domain.Proveedor;
import todo1.com.services.ProveedorService;

@WebMvcTest(ProveedorController.class)
class ProveedorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("proveedorService")
	@MockBean
	private ProveedorService proveedorService;

	@Test
	public void testFindAll() throws Exception {
		List<Proveedor> listProveedor = new ArrayList<>();
		listProveedor.add(new Proveedor(1L, "proveedor1", "direccion1", "0998302415", true, new Date(), null));
		listProveedor.add(new Proveedor(2L, "proveedor1", "direccion1", "0998302415", true, new Date(), null));
		listProveedor.add(new Proveedor(3L, "proveedor1", "direccion1", "0998302415", true, new Date(), null));
		Mockito.when(proveedorService.findAll()).thenReturn(listProveedor);
		String url = "/proveedor/";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(listProveedor);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testPostEntity() throws JsonProcessingException, Exception {
		Proveedor newProveedor = new Proveedor(1L, "proveedor1", "direccion1", "0998302415", true, new Date(), null);
		Proveedor savedProveedor = new Proveedor(1L, "proveedor1", "direccion1", "0998302415", true, new Date(), null);
		Mockito.when(proveedorService.save(newProveedor)).thenReturn(savedProveedor);
		String url = "/proveedor/";
		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(newProveedor))
				.with(csrf())).andExpect(status().isCreated())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Creado\"}"));
	}

	@Test
	void testFindById() throws Exception {
		Long proveedorId = 1L;
		Optional<Proveedor> findCliente = Optional
				.of(new Proveedor(1L, "proveedor1", "direccion1", "0998302415", true, new Date(), null));
		Mockito.when(proveedorService.findById(proveedorId)).thenReturn(findCliente);
		String url = "/proveedor/" + proveedorId;
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(findCliente);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testUpdate() throws Exception {
		Proveedor exitProveedor = new Proveedor(1L, "proveedor1", "direccion1", "0998302415", true, new Date(), null);
		Proveedor updateProveedor = new Proveedor(1L, "proveedor1", "direccion1", "0998302415", true, new Date(), null);
		Mockito.when(proveedorService.update(exitProveedor)).thenReturn(updateProveedor);
		String url = "/proveedor/";
		mockMvc.perform(put(url).contentType("application/json").content(objectMapper.writeValueAsString(exitProveedor))
				.with(csrf())).andExpect(status().isOk())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Actualizado\"}")).andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		Integer proveedorId = 1;
		Mockito.doNothing().when(proveedorService).delete((long) proveedorId);
		String url = "/proveedor/" + proveedorId;
		mockMvc.perform(delete(url)).andExpect(status().isOk());
		Mockito.verify(proveedorService, times(1)).delete((long) proveedorId);
	}

}
