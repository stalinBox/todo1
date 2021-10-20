package ec.gob.mag.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.domain.Area;
import ec.gob.mag.domain.constraint.AreaCreate;
import ec.gob.mag.domain.constraint.AreaUpdate;
import ec.gob.mag.domain.constraint.RegisterAudit;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.services.AreaService;
import ec.gob.mag.util.ConvertEntityUtil;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/area")
@Api(value = "Rest Api example", tags = "AREA")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class AreaController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

	@Autowired
	@Qualifier("areaService")
	private AreaService areaService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

	@Autowired
	@Qualifier("convertEntityUtil")
	private ConvertEntityUtil convertEntityUtil;

	@Autowired
	@Qualifier("util")
	private Util util;

	/**
	 * Busca todos los registros de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return Entidad: Retorna todos los registros.
	 * @RequestHeader(name = "Authorization") String token
	 */
	@GetMapping(value = "/findAll")
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = Area.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> findAll(@RequestHeader(name = "Authorization") String token) {
		List<Area> officer = areaService.findAll();
		LOGGER.info("template/findAll: " + officer.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(officer);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@GetMapping(value = "/findById/{id}")
	@ApiOperation(value = "Obtiene el registro por id", response = Area.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Optional<?>> findById(@Validated @PathVariable Long id,
			@RequestHeader(name = "Authorization") String token) {
		Optional<Area> officer = areaService.findById(id, false, Constante.REGISTRO_ACTIVO.getCodigo());
		LOGGER.info("findById: " + officer.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(officer);
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@PostMapping(value = "/create/")
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseController> postEntity(@Validated @RequestBody AreaCreate create,
			@RequestHeader(name = "Authorization") String token) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, IOException {
		Area objectoValidado = convertEntityUtil.ConvertSingleEntityGET(Area.class, (Object) create);
		Area off = areaService.save(objectoValidado);
		LOGGER.info("Creado: " + objectoValidado + " usuario: " + objectoValidado.getAreRegUsu());
		return ResponseEntity.ok(new ResponseController(off.getAreId(), "Creado"));
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param usuId:   Identificador del usuario que va a actualizar
	 * 
	 * @param entidad: entidad a actualizar
	 * @return ResponseController: Retorna el id actualizado
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@PutMapping(value = "/update/")
	@ApiOperation(value = "Actualizar los registros", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseController> update(@Validated @RequestBody AreaUpdate update,
			@RequestHeader(name = "Authorization") String token) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, IOException {
		Area objectoValidado = convertEntityUtil.ConvertSingleEntityGET(Area.class, (Object) update);
		Area off = areaService.update(objectoValidado);
		LOGGER.info("Actualizado: " + off + " usuario: " + update.getAreActUsu());
		return ResponseEntity.ok(new ResponseController(off.getAreId(), "Actualizado"));
	}

	/**
	 * Realiza un mantenimiento del estado del registro
	 * 
	 * @param RegisterAudit: Identificador del registro contiene
	 *                       id,actUsu,eliminado,estado,desc
	 * @return ResponseController: Retorna el id eliminado
	 */
	@PutMapping(value = "/state-record/")
	@ApiOperation(value = "Gestionar estado del registro de la tabla CIALCO, ciaEstado={11 ACTIVO,12 INACTIVO}, ciaEliminado={false, true}, state: {disable, delete, activate}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> stateCialco(@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody RegisterAudit audit) {
		Area row = areaService.findByIdAll(audit.getId()).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("Cialco", "Id", audit.getId().toString()));
		row.setAreEliminado(audit.getEliminado());
		row.setAreEstado(audit.getEstado());
		row.setAreActUsu(audit.getActUsu());
		Area areaDel = areaService.save(row);
		LOGGER.info("cialco state-record : " + audit.getId() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(areaDel.getAreId(), audit.getDesc()));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
