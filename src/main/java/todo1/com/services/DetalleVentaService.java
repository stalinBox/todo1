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

import todo1.com.domain.DetalleVenta;
import todo1.com.exception.MyNotFoundException;
import todo1.com.repository.DetalleVentaRepository;

@Service("detalleVentaService")
public class DetalleVentaService {

	@Autowired
	@Qualifier("detalleVentaRepository")
	private DetalleVentaRepository detalleVentaRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(DetalleVenta org) {
		org.getVenta().setCliente(null);
		org.getVenta().setDetalleVenta(null);
		org.getProducto().setDetalleCompra(null);
		org.getProducto().setDetalleVenta(null);
		org.getProducto().setSubGrupo(null);
	}

	public List<DetalleVenta> clearListLazyVariables(List<DetalleVenta> orgs) {
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
	 * @return detalleVenta: Todos los registros de la tabla
	 */
	public List<DetalleVenta> findAll() {
		List<DetalleVenta> detalleVenta = (List<DetalleVenta>) detalleVentaRepository.findAll();
		if (detalleVenta.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(detalleVenta);
		return detalleVenta;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return cliente: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<DetalleVenta> findById(Long id) {
		Optional<DetalleVenta> detalleVenta = detalleVentaRepository.findById(id);
		if (!detalleVenta.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(detalleVenta.get());
		return detalleVenta;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param detalleVenta: Contiene todos campos de la entidad para guardar
	 * @return DetalleVenta: La entidad Guardada
	 */
	public DetalleVenta save(DetalleVenta detalleVenta) {
		return detalleVentaRepository.save(detalleVenta);
	}

	/**
	 * Elimina un registro por Id
	 * 
	 * @param id: Identificador del registro
	 */
	public void delete(Long id) {
		detalleVentaRepository.deleteById(id);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public DetalleVenta update(DetalleVenta newDetalleVenta) {
		Optional<DetalleVenta> oldDetalleVenta = findById(newDetalleVenta.getDetv_id());
		copyNonNullProperties(newDetalleVenta, oldDetalleVenta.get());
		return detalleVentaRepository.save(oldDetalleVenta.get());
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
