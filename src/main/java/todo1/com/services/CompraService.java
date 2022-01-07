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

import todo1.com.domain.Compra;
import todo1.com.exception.MyNotFoundException;
import todo1.com.repository.CompraRepository;

@Service("compraService")
public class CompraService {

	@Autowired
	@Qualifier("compraRepository")
	private CompraRepository compraRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Compra org) {
		org.getProveedor().setCompra(null);
		org.getDetalleCompra().stream().map(mpr -> {
			mpr.setCompra(null);
			mpr.setProducto(null);
			return mpr;
		}).collect(Collectors.toList());
//		org.getDetalleCompra().stream().map(mpr -> {
//			mpr.setProducto(null);
//			mpr.setCompra(null);
//			return mpr;
//		}).collect(Collectors.toList());
	}

	public List<Compra> clearListLazyVariables(List<Compra> orgs) {
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
	 * @return compra:Todos los registros de la tabla
	 */
	public List<Compra> findAll() {
		List<Compra> compra = (List<Compra>) compraRepository.findAll();
		if (compra.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(compra);
		return compra;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return compra: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Compra> findById(Long id) {
		Optional<Compra> compra = compraRepository.findById(id);
		if (!compra.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(compra.get());
		return compra;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param compra: Contiene todos campos de la entidad para guardar
	 * @return Compra: La entidad Guardada
	 */
	public Compra save(Compra compra) {
		return compraRepository.save(compra);
	}

	/**
	 * Elimina un registro por Id
	 * 
	 * @param id: Identificador del registro
	 */
	public void delete(Long id) {
		compraRepository.deleteById(id);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param newCompra: Contiene todos campos de la entidad para guardar
	 * @return Compra: La entidad Guardada
	 */
	public Compra update(Compra newCompra) {
		Optional<Compra> oldCompra = findById(newCompra.getComp_id());
		copyNonNullProperties(newCompra, oldCompra.get());
		return compraRepository.save(oldCompra.get());
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
