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

import ec.gob.mag.domain.Cumpleanios;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.CumpleaniosRepository;

@Service("cumpleaniosService")
public class CumpleaniosService {

	@Autowired
	@Qualifier("cumpleaniosRepository")
	private CumpleaniosRepository cumpleaniosRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Cumpleanios org) {
		org.getArea().setNoticias(null);
		org.getArea().setCumpleanios(null);
	}

	public List<Cumpleanios> clearListLazyVariables(List<Cumpleanios> orgs) {
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
	public Optional<Cumpleanios> findByIdAll(Long id) {
		Optional<Cumpleanios> fun = cumpleaniosRepository.findById(id);
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
	public List<Cumpleanios> findAll() {
		List<Cumpleanios> template = cumpleaniosRepository.findByCumEliminadoAndCumEstadoEquals(false, 11);
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
	public Optional<Cumpleanios> findById(Long id) {
		Optional<Cumpleanios> template = cumpleaniosRepository.findByCumIdAndCumEliminadoAndCumEstadoEquals(id, false,
				Constante.REGISTRO_ACTIVO.getCodigo());
		if (!template.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(template.get());
		return template;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Cumpleanios save(Cumpleanios officer) {
		return cumpleaniosRepository.save(officer);
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Cumpleanios update(Cumpleanios newEntity) {
		Optional<Cumpleanios> oldEntity = findById(newEntity.getCumId());
		copyNonNullProperties(newEntity, oldEntity.get());
		return cumpleaniosRepository.save(oldEntity.get());
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
