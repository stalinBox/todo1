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

import todo1.com.domain.Producto;
import todo1.com.domain.SubGrupo;
import todo1.com.services.ProductoService;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("productoService")
	@MockBean
	private ProductoService productoService;

	@Test
	public void testFindAll() throws Exception {
		SubGrupo sbGrupo = new SubGrupo();
		sbGrupo.setSgrup_id(1L);
		List<Producto> listProducto = new ArrayList<>();
		listProducto.add(new Producto(1L, "producto1", "Desc procto1", 99.2, 101.00, 200, true, new Date(), sbGrupo,
				null, null));
		listProducto.add(new Producto(2L, "producto1", "Desc procto1", 100.2, 150.00, 300, true, new Date(), sbGrupo,
				null, null));
		listProducto.add(
				new Producto(3L, "producto1", "Desc procto1", 80.2, 120.00, 5, true, new Date(), sbGrupo, null, null));
		Mockito.when(productoService.findAll()).thenReturn(listProducto);
		String url = "/producto/";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(listProducto);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testPostEntity() throws JsonProcessingException, Exception {
		SubGrupo sbGrupo = new SubGrupo();
		sbGrupo.setSgrup_id(1L);
		Producto newProducto = new Producto(1L, "producto1", "Desc procto1", 99.2, 101.00, 200, true, new Date(),
				sbGrupo, null, null);
		Producto savedProducto = new Producto(1L, "producto1", "Desc procto1", 99.2, 101.00, 200, true, new Date(),
				sbGrupo, null, null);
		Mockito.when(productoService.save(newProducto)).thenReturn(savedProducto);
		String url = "/producto/";
		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(newProducto))
				.with(csrf())).andExpect(status().isCreated())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Creado\"}"));
	}

	@Test
	void testFindById() throws Exception {
		SubGrupo sbGrupo = new SubGrupo();
		sbGrupo.setSgrup_id(1L);
		Long productoId = 1L;
		Optional<Producto> findProducto = Optional.of(new Producto(1L, "producto1", "Desc procto1", 99.2, 101.00, 200,
				true, new Date(), sbGrupo, null, null));
		Mockito.when(productoService.findById(productoId)).thenReturn(findProducto);
		String url = "/producto/" + productoId;
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(findProducto);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testUpdate() throws Exception {
		SubGrupo sbGrupo = new SubGrupo();
		sbGrupo.setSgrup_id(1L);
		Producto exitProducto = new Producto(1L, "producto1", "Desc procto1", 99.2, 101.00, 200, true, new Date(),
				sbGrupo, null, null);
		Producto updateProducto = new Producto(1L, "producto1", "Desc procto1", 99.2, 101.00, 200, true, new Date(),
				sbGrupo, null, null);
		Mockito.when(productoService.update(exitProducto)).thenReturn(updateProducto);
		String url = "/producto/";
		mockMvc.perform(put(url).contentType("application/json").content(objectMapper.writeValueAsString(exitProducto))
				.with(csrf())).andExpect(status().isOk())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Actualizado\"}")).andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		Integer productoId = 1;
		Mockito.doNothing().when(productoService).delete((long) productoId);
		String url = "/producto/" + productoId;
		mockMvc.perform(delete(url)).andExpect(status().isOk());
		Mockito.verify(productoService, times(1)).delete((long) productoId);
	}

}
