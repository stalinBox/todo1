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

import ec.gob.mag.domain.Officer;
import ec.gob.mag.services.OfficerService;
import ec.gob.mag.util.ResponseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/officer")
@Api(value = "Rest Api example", tags = "OFFICER")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class MicroController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(MicroController.class);

	@Autowired
	@Qualifier("officerService")
	private OfficerService officerService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

	/**
	 * Busca todos los registros de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return Entidad: Retorna todos los registros
	 */
	@GetMapping(value = "/findAll")
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = Officer.class)
	public ResponseEntity<List<Officer>> findAll(@RequestHeader(name = "Authorization") String token) {
		List<Officer> officer = officerService.findAll();
		LOGGER.info("Find All: " + officer.toString());
		return ResponseEntity.ok(officer);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@GetMapping(value = "/findById/{id}")
	@ApiOperation(value = "Get Officer by id", response = Officer.class)
	public ResponseEntity<Optional<Officer>> findById(@Validated @PathVariable Long id,
			@RequestHeader(name = "Authorization") String token) {
		Optional<Officer> officer = officerService.findById(id);
		LOGGER.info("Find By Id: " + officer.toString());
		return ResponseEntity.ok(officer);
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
	public ResponseEntity<ResponseController> update(@Valid @RequestBody Officer updateOfficer,
			@PathVariable Long usuId, @RequestHeader(name = "Authorization") String token) {
		updateOfficer.setCampoActUsu(usuId);
		Officer off = officerService.update(updateOfficer);
		LOGGER.info("Update: " + off + " update by: " + usuId);
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
	public ResponseEntity<ResponseController> deleteOfficer(@Validated @PathVariable Long id, @PathVariable Long usuId,
			@RequestHeader(name = "Authorization") String token) {
		Officer deleteOfficer = officerService.findById(id)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Officer", "Id", id.toString()));
		deleteOfficer.setCampoEliminado(true);
		deleteOfficer.setCampoActUsu(usuId);
		Officer officerDel = officerService.save(deleteOfficer);
		LOGGER.info("Delete Officer id: " + id + " delete by: " + usuId);
		return ResponseEntity.ok(new ResponseController(officerDel.getId(), "eliminado"));
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@PostMapping(value = "/create/")
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	public ResponseEntity<ResponseController> postOfficer(@Validated @RequestBody Officer officer,
			@RequestHeader(name = "Authorization") String token) {
		Officer off = officerService.save(officer);
		LOGGER.info("Officer Save: " + officer);
		return ResponseEntity.ok(new ResponseController(off.getId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
