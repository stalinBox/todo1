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

import todo1.com.domain.SubGrupo;
import todo1.com.exception.MyNotFoundException;
import todo1.com.repository.SubGrupoRepository;

@Service("subGrupoService")
public class SubGrupoService {

	@Autowired
	@Qualifier("subGrupoRepository")
	private SubGrupoRepository subGrupoRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(SubGrupo org) {
//		org.getGrupo().stream().map(mpr -> {
//			mpr.setSubGrupo(null);
//			return mpr;
//		}).collect(Collectors.toSet());
		org.setProducto(null);
		org.getGrupo().setSubGrupo(null);
	}

	public List<SubGrupo> clearListLazyVariables(List<SubGrupo> orgs) {
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
	 * @return sbGrupo: Todos los registros de la tabla
	 */
	public List<SubGrupo> findAll() {
		List<SubGrupo> sbGrupo = (List<SubGrupo>) subGrupoRepository.findAll();
		if (sbGrupo.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(sbGrupo);
		return sbGrupo;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return sbGrupo: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<SubGrupo> findById(Long id) {
		Optional<SubGrupo> sbGrupo = subGrupoRepository.findById(id);
		if (!sbGrupo.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(sbGrupo.get());
		return sbGrupo;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param sbGrupo: Contiene todos campos de la entidad para guardar
	 * @return SubGrupo: La entidad Guardada
	 */
	public SubGrupo save(SubGrupo sbGrupo) {
		return subGrupoRepository.save(sbGrupo);
	}

	/**
	 * Elimina un registro por Id
	 * 
	 * @param id: Identificador del registro
	 */
	public void delete(Long id) {
		subGrupoRepository.deleteById(id);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return SubGrupo: La entidad Guardada
	 */
	public SubGrupo update(SubGrupo newSbGrupo) {
		Optional<SubGrupo> oldSbGrupo = findById(newSbGrupo.getSgrup_id());
		copyNonNullProperties(newSbGrupo, oldSbGrupo.get());
		return subGrupoRepository.save(oldSbGrupo.get());
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
