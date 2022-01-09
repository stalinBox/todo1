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

import todo1.com.domain.Grupo;
import todo1.com.domain.SubGrupo;
import todo1.com.services.SubGrupoService;

@WebMvcTest(SubGrupoController.class)
class SubGrupoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("subGrupoService")
	@MockBean
	private SubGrupoService subGrupoService;

	@Test
	public void testFindAll() throws Exception {
		Grupo grupo = new Grupo();
		grupo.setGrup_id(1L);
		List<SubGrupo> listSubGrupo = new ArrayList<>();
		listSubGrupo.add(new SubGrupo(1L, "subGrupo1", true, new Date(), grupo, null));
		listSubGrupo.add(new SubGrupo(1L, "subGrupo1", true, new Date(), grupo, null));
		listSubGrupo.add(new SubGrupo(1L, "subGrupo1", true, new Date(), grupo, null));
		Mockito.when(subGrupoService.findAll()).thenReturn(listSubGrupo);
		String url = "/subGrupo/";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(listSubGrupo);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testPostEntity() throws JsonProcessingException, Exception {
		Grupo grupo = new Grupo();
		grupo.setGrup_id(1L);
		SubGrupo newGrupo = new SubGrupo(1L, "subGrupo1", true, new Date(), grupo, null);
		SubGrupo savedGrupo = new SubGrupo(1L, "subGrupo1", true, new Date(), grupo, null);
		Mockito.when(subGrupoService.save(newGrupo)).thenReturn(savedGrupo);
		String url = "/subGrupo/";
		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(newGrupo))
				.with(csrf())).andExpect(status().isCreated())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Creado\"}"));
	}

	@Test
	void testFindById() throws Exception {
		Grupo grupo = new Grupo();
		grupo.setGrup_id(1L);
		Long clienteId = 1L;
		Optional<SubGrupo> findCliente = Optional.of(new SubGrupo(1L, "subGrupo1", true, new Date(), grupo, null));
		Mockito.when(subGrupoService.findById(clienteId)).thenReturn(findCliente);
		String url = "/subGrupo/" + clienteId;
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(findCliente);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testUpdate() throws Exception {
		Grupo grupo = new Grupo();
		grupo.setGrup_id(1L);
		SubGrupo exitSubGrupo = new SubGrupo(1L, "subGrupo1", true, new Date(), grupo, null);
		SubGrupo updateSubGrupo = new SubGrupo(1L, "subGrupo1", true, new Date(), grupo, null);
		Mockito.when(subGrupoService.update(exitSubGrupo)).thenReturn(updateSubGrupo);
		String url = "/subGrupo/";
		mockMvc.perform(put(url).contentType("application/json").content(objectMapper.writeValueAsString(exitSubGrupo))
				.with(csrf())).andExpect(status().isOk())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Actualizado\"}")).andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		Integer clienteId = 1;
		Mockito.doNothing().when(subGrupoService).delete((long) clienteId);
		String url = "/subGrupo/" + clienteId;
		mockMvc.perform(delete(url)).andExpect(status().isOk());
		Mockito.verify(subGrupoService, times(1)).delete((long) clienteId);
	}

}
