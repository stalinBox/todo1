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

import todo1.com.domain.Proveedor;
import todo1.com.exception.MyNotFoundException;
import todo1.com.repository.ProveedorRepository;

@Service("proveedorService")
public class ProveedorService {

	@Autowired
	@Qualifier("proveedorRepository")
	private ProveedorRepository proveedorRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Proveedor org) {
		org.setCompra(null);
	}

	public List<Proveedor> clearListLazyVariables(List<Proveedor> orgs) {
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
	 * @return producto: Todos los registros de la tabla
	 */
	public List<Proveedor> findAll() {
		List<Proveedor> proveedor = (List<Proveedor>) proveedorRepository.findAll();
		if (proveedor.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(proveedor);
		return proveedor;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return producto: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Proveedor> findById(Long id) {
		Optional<Proveedor> proveedor = proveedorRepository.findById(id);
		if (!proveedor.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(proveedor.get());
		return proveedor;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param producto: Contiene todos campos de la entidad para guardar
	 * @return Proveedor: La entidad Guardada
	 */
	public Proveedor save(Proveedor proveedor) {
		return proveedorRepository.save(proveedor);
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 */
	public void delete(Long id) {
		proveedorRepository.deleteById(id);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Proveedor update(Proveedor newProveedor) {
		Optional<Proveedor> oldProveedor = findById(newProveedor.getProve_id());
		copyNonNullProperties(newProveedor, oldProveedor.get());
		return proveedorRepository.save(oldProveedor.get());
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
