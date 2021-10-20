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

import ec.gob.mag.domain.Area;
import ec.gob.mag.domain.constraint.AreaUpdate;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.AreaRepository;

@Service("areaService")
public class AreaService {

	@Autowired
	@Qualifier("areaRepository")
	private AreaRepository areaRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Area org) {
		org.getCumpleanios().stream().map(u -> {
			u.setArea(null);
			return u;
		}).collect(Collectors.toList());

		org.getNoticias().stream().map(u -> {
			u.setArea(null);
			return u;
		}).collect(Collectors.toList());

	}

	public List<Area> clearListLazyVariables(List<Area> orgs) {
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
	public Optional<Area> findByIdAll(Long id) {
		Optional<Area> cialco = areaRepository.findById(id);
		if (!cialco.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(cialco.get());
		return cialco;
	}

	/**
	 * Metodo para encontrar todos los registros
	 * 
	 * @return Todos los registros de la tabla
	 */
	public List<Area> findAll() {
		List<Area> template = areaRepository.findByAreEliminadoAndAreEstadoEquals(false, 11);
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
	public Optional<Area> findById(Long id, Boolean eliminado, Integer estado) {
		Optional<Area> cialco = areaRepository.findByAreIdAndAreEliminadoAndAreEstadoEquals(id, eliminado, estado);
		if (!cialco.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(cialco.get());
		return cialco;
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Area update(Area newEnity) {
		Optional<Area> oldCialco = findById(newEnity.getAreId(), false, Constante.REGISTRO_ACTIVO.getCodigo());
		copyNonNullProperties(newEnity, oldCialco.get());
		return areaRepository.save(oldCialco.get());
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

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Area save(Area officer) {
		return areaRepository.save(officer);
	}
}
