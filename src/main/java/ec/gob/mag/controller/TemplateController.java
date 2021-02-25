package ec.gob.mag.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.domain.Template;
import ec.gob.mag.services.TemplateService;
import ec.gob.mag.util.ResponseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/template")
@Api(value = "Rest Api example", tags = "TEMPLATE")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class TemplateController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);

	@Autowired
	@Qualifier("templateService")
	private TemplateService templateService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

	/**
	 * Busca todos los registros de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return Entidad: Retorna todos los registros.
	 * @RequestHeader(name = "Authorization") String token
	 */
	@GetMapping(value = "/findAll/{usuId}")
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = Template.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Template>> findAll(@Validated @PathVariable Long usuId) {
		List<Template> officer = templateService.findAll();
		LOGGER.info("findAll: " + officer.toString() + " usuario: " + usuId);
		return ResponseEntity.ok(officer);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@GetMapping(value = "/findById/{id}/{usuId}")
	@ApiOperation(value = "Get Template by id", response = Template.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Optional<Template>> findById(@Validated @PathVariable Long id,
			@Validated @PathVariable Long usuId, @RequestHeader(name = "Authorization") String token) {
		Optional<Template> officer = templateService.findById(id);
		LOGGER.info("findById: " + officer.toString() + " usuario: " + usuId);
		return ResponseEntity.ok(officer);
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@PostMapping(value = "/create/")
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseController> postEntity(@RequestBody Template template,
			@RequestHeader(name = "Authorization") String token) {
		Template off = templateService.save(template);
		LOGGER.info("Template Save: " + template + "usuario: " + template.getTmpRegUsu());
		return ResponseEntity.ok(new ResponseController(off.getId(), "Creado"));
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param usuId:   Identificador del usuario que va a actualizar
	 * 
	 * @param entidad: entidad a actualizar
	 * @return ResponseController: Retorna el id actualizado
	 */
	@PostMapping(value = "/update/{usuId}")
	@ApiOperation(value = "Actualizar los registros", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseController> update(@Valid @RequestBody Template update, @PathVariable Long usuId,
			@RequestHeader(name = "Authorization") String token) {
		update.setTmpActUsu(usuId);
		Template off = templateService.update(update);
		LOGGER.info("Update: " + off + " usuario: " + usuId);
		return ResponseEntity.ok(new ResponseController(off.getId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@GetMapping(value = "/delete/{id}/{usuId}")
	@ApiOperation(value = "Remove officers by id")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> deleteOfficer(@Validated @PathVariable Long id, @PathVariable Long usuId,
			@RequestHeader(name = "Authorization") String token) {
		Template deleteTemplate = templateService.findById(id)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Template", "Id", id.toString()));
		deleteTemplate.setTmpEliminado(true);
		deleteTemplate.setTmpActUsu(usuId);
		Template officerDel = templateService.save(deleteTemplate);
		LOGGER.info("Delete: " + id + " usuario: " + usuId);
		return ResponseEntity.ok(new ResponseController(officerDel.getId(), "eliminado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
