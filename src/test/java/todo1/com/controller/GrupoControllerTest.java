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
import todo1.com.services.GrupoService;

@WebMvcTest(GrupoController.class)
class GrupoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("grupoService")
	@MockBean
	private GrupoService grupoService;

	@Test
	public void testFindAll() throws Exception {
		List<Grupo> listGrupo = new ArrayList<>();
		listGrupo.add(new Grupo(1L, "NombreGrupo1", true, new Date(), null));
		listGrupo.add(new Grupo(2L, "NombreGrupo2", true, new Date(), null));
		listGrupo.add(new Grupo(3L, "NombreGrupo3", true, new Date(), null));
		Mockito.when(grupoService.findAll()).thenReturn(listGrupo);
		String url = "/grupo/";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(listGrupo);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testPostEntity() throws JsonProcessingException, Exception {
		Grupo newGrupo = new Grupo(1L, "NombreGrupo1", true, new Date(), null);
		Grupo savedGrupo = new Grupo(1L, "NombreGrupo1", true, new Date(), null);
		Mockito.when(grupoService.save(newGrupo)).thenReturn(savedGrupo);
		String url = "/grupo/";
		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(newGrupo))
				.with(csrf())).andExpect(status().isCreated())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Creado\"}"));
	}

	@Test
	void testFindById() throws Exception {
		Long grupoId = 1L;
		Optional<Grupo> findGrupo = Optional.of(new Grupo(1L, "NombreGrupo1", true, new Date(), null));
		Mockito.when(grupoService.findById(grupoId)).thenReturn(findGrupo);
		String url = "/grupo/" + grupoId;
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(findGrupo);
		assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	void testUpdate() throws Exception {
		Grupo exitGrupo = new Grupo(1L, "NombreGrupo1", true, new Date(), null);
		Grupo updateGrupo = new Grupo(1L, "NombreGrupo333", true, new Date(), null);
		Mockito.when(grupoService.update(exitGrupo)).thenReturn(updateGrupo);
		String url = "/grupo/";
		mockMvc.perform(put(url).contentType("application/json").content(objectMapper.writeValueAsString(exitGrupo))
				.with(csrf())).andExpect(status().isOk())
				.andExpect(content().string("{\"id\":" + 1L + ",\"estado\":\"Actualizado\"}")).andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		Integer grupoId = 1;
		Mockito.doNothing().when(grupoService).delete((long) grupoId);
		String url = "/grupo/" + grupoId;
		mockMvc.perform(delete(url)).andExpect(status().isOk());
		Mockito.verify(grupoService, times(1)).delete((long) grupoId);
	}

}
