package todo1.com.services;

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

import todo1.com.domain.Grupo;
import todo1.com.exception.MyNotFoundException;
import todo1.com.repository.GrupoRepository;

@Service("grupoService")
public class GrupoService {

	@Autowired
	@Qualifier("grupoRepository")
	private GrupoRepository grupoRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Grupo org) {
		org.setSubGrupo(null);
	}

	public List<Grupo> clearListLazyVariables(List<Grupo> orgs) {
		if (orgs != null)
			orgs = orgs.stream().map(u -> {
				clearObjectLazyVariables(u);
				return u;
			}).collect(Collectors.toList());
		return orgs;
	}

	/**
	 * Metodo para encontrar todos los registros
	 * 
	 * @return grupo: Todos los registros de la tabla
	 */
	public List<Grupo> findAll() {
		List<Grupo> grupo = (List<Grupo>) grupoRepository.findAll();
		if (grupo.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(grupo);
		return grupo;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return grupo: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Grupo> findById(Long id) {
		Optional<Grupo> grupo = grupoRepository.findById(id);
		if (!grupo.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(grupo.get());
		return grupo;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param grupo: Contiene todos campos de la entidad para guardar
	 * @return Grupo: La entidad Guardada
	 */
	public Grupo save(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 */
	public void delete(Long id) {
		grupoRepository.deleteById(id);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Grupo update(Grupo newGrupo) {
		Optional<Grupo> oldGrupo = findById(newGrupo.getGrup_id());
		copyNonNullProperties(newGrupo, oldGrupo.get());
		return grupoRepository.save(oldGrupo.get());
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
