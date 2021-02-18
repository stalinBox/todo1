package ec.gob.mag.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.domain.Template;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.TemplateRepository;

@Service("templateService")
public class TemplateService {

	@Autowired
	@Qualifier("templateRepository")
	private TemplateRepository templateRepository;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Metodo para encontrar todos los registros
	 * 
	 * @return Todos los registros de la tabla
	 */
	public List<Template> findAll() {
		List<Template> template = templateRepository.findByTmpEliminadoAndTmpEstadoEquals(false, 11);
		if (template.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		return template;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Template> findById(Long id) {
		Optional<Template> template = templateRepository.findByIdAndTmpEliminadoAndTmpEstadoEquals(id, false, 11);
		if (!template.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return template;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Template update(Template template) {
		Optional<Template> off = findById(template.getId());
		if (!off.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					off.get().getId()));
		return templateRepository.save(template);
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Template save(Template officer) {
		return templateRepository.save(officer);
	}
}
