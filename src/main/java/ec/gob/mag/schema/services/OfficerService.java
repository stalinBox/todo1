package ec.gob.mag.schema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.schema.domain.Officer;
import ec.gob.mag.schema.exception.NotFoundException;
import ec.gob.mag.schema.repository.OfficerRepository;

@Service("officerService")
public class OfficerService {

	@Autowired
	@Qualifier("officerRepository")
	private OfficerRepository officerRepository;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Metodo para encontrar todos los registros
	 * 
	 * @return Todos los registros de la tabla
	 */
	public List<Officer> findAll() {
		List<Officer> officer = officerRepository.findByCampoEliminadoAndCampoEstadoEquals(false, 11);
		if (officer.isEmpty())
			throw new NotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		return officer;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Officer> findById(Long id) {
		Optional<Officer> officer = officerRepository.findByIdAndCampoEliminadoAndCampoEstadoEquals(id, false, 11);
		if (!officer.isPresent())
			throw new NotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return officer;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Officer save(Officer officer) {
		return officerRepository.save(officer);
	}
}
