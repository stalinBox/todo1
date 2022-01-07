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

import todo1.com.domain.DetalleCompra;
import todo1.com.exception.MyNotFoundException;
import todo1.com.repository.DetalleCompraRepository;

@Service("detalleCompraService")
public class DetalleCompraService {

	@Autowired
	@Qualifier("detalleCompraRepository")
	private DetalleCompraRepository detalleCompraRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(DetalleCompra org) {
		org.getCompra().setDetalleCompra(null);
		org.getCompra().setProveedor(null);
		org.getProducto().setDetalleCompra(null);
		org.getProducto().setDetalleVenta(null);
		org.getProducto().setSubGrupo(null);
//		org.getProducto().stream().map(mpr -> {
//			mpr.setSubGrupo(null);
//			mpr.setDetalleCompra(null);
//			mpr.setDetalleVenta(null);
//			return mpr;
//		}).collect(Collectors.toSet());
	}

	public List<DetalleCompra> clearListLazyVariables(List<DetalleCompra> orgs) {
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
	 * @return Todos los registros de la tabla detalleCompra
	 */
	public List<DetalleCompra> findAll() {
		List<DetalleCompra> detalleCompra = (List<DetalleCompra>) detalleCompraRepository.findAll();
		if (detalleCompra.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(detalleCompra);
		return detalleCompra;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return DetalleCompra: Retorna todos los registros filtrados por el
	 *         parámetros de entrada
	 */
	public Optional<DetalleCompra> findById(Long id) {
		Optional<DetalleCompra> detalleCompra = detalleCompraRepository.findById(id);
		if (!detalleCompra.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(detalleCompra.get());
		return detalleCompra;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param detalleCompra: Contiene todos campos de la entidad para guardar
	 * @return DetalleCompra: La entidad Guardada
	 */
	public DetalleCompra save(DetalleCompra detalleCompra) {
		return detalleCompraRepository.save(detalleCompra);
	}

	/**
	 * Elimina un registro por Id
	 * 
	 * @param id: Identificador del registro
	 */
	public void delete(Long id) {
		detalleCompraRepository.deleteById(id);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public DetalleCompra update(DetalleCompra newDetalleCompra) {
		Optional<DetalleCompra> oldDetalleCompra = findById(newDetalleCompra.getDetc_id());
		copyNonNullProperties(newDetalleCompra, oldDetalleCompra.get());
		return detalleCompraRepository.save(oldDetalleCompra.get());
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
