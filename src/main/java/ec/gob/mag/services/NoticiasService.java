package ec.gob.mag.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.domain.Noticias;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.NoticiasRepository;

@Service("noticiasService")
public class NoticiasService {

	@Autowired
	@Qualifier("noticiasRepository")
	private NoticiasRepository noticiasRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Noticias org) {

		org.getArea().setCumpleanios(null);
		org.getArea().setNoticias(null);
	}

	public List<Noticias> clearListLazyVariables(List<Noticias> orgs) {
		if (orgs != null)
			orgs = orgs.stream().map(u -> {
				clearObjectLazyVariables(u);
				return u;
			}).collect(Collectors.toList());
		return orgs;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Noticias> findByIdAll(Long id) {
		Optional<Noticias> fun = noticiasRepository.findById(id);
		if (!fun.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return fun;
	}

	/**
	 * Metodo para encontrar todos los registros
	 * 
	 * @return Todos los registros de la tabla
	 */
	public List<Noticias> findAll() {
		List<Noticias> template = noticiasRepository.findByNotEliminadoAndNotEstadoEquals(false, 11);
		if (template.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(template);
		return template;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Noticias> findById(Long id) {
		Optional<Noticias> template = noticiasRepository.findByNotIdAndNotEliminadoAndNotEstadoEquals(id, false, 11);
		if (!template.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(template.get());
		return template;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Noticias update(Noticias newEntity) {
		Optional<Noticias> oldEntity = findById(newEntity.getNotId());
		copyNonNullProperties(newEntity, oldEntity.get());
		return noticiasRepository.save(oldEntity.get());
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Noticias save(Noticias officer) {
		return noticiasRepository.save(officer);
	}

	public static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
