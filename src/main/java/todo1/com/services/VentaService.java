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

import todo1.com.domain.Venta;
import todo1.com.exception.MyNotFoundException;
import todo1.com.repository.VentaRepository;

@Service("ventaService")
public class VentaService {

	@Autowired
	@Qualifier("ventaRepository")
	private VentaRepository ventaRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Venta org) {

		org.getCliente().setVenta(null);
		org.getDetalleVenta().stream().map(mpr -> {
			mpr.setProducto(null);
			mpr.setVenta(null);
			return mpr;
		}).collect(Collectors.toSet());
//		org.getDetalleVenta().stream().map(mpr -> {
//			mpr.setVenta(null);
//			mpr.setProducto(null);
//			return mpr;
//		}).collect(Collectors.toSet());
	}

	public List<Venta> clearListLazyVariables(List<Venta> orgs) {
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
	 * @return venta: Todos los registros de la tabla
	 */
	public List<Venta> findAll() {
		List<Venta> venta = (List<Venta>) ventaRepository.findAll();
		if (venta.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(venta);
		return venta;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return venta: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Venta> findById(Long id) {
		Optional<Venta> venta = ventaRepository.findById(id);
		if (!venta.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(venta.get());
		return venta;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param venta: Contiene todos campos de la entidad para guardar
	 * @return Venta: La entidad Guardada
	 */
	public Venta save(Venta venta) {
		return ventaRepository.save(venta);
	}

	/**
	 * Elimina un registro por Id
	 * 
	 * @param id: Identificador del registro
	 */
	public void delete(Long id) {
		ventaRepository.deleteById(id);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return Venta: La entidad Guardada
	 */
	public Venta update(Venta newVenta) {
		Optional<Venta> oldVenta = findById(newVenta.getVent_id());
		copyNonNullProperties(newVenta, oldVenta.get());
		return ventaRepository.save(oldVenta.get());
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
