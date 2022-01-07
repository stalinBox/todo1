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

import todo1.com.domain.Producto;
import todo1.com.exception.MyNotFoundException;
import todo1.com.repository.ProductoRepository;

@Service("productoService")
public class ProductoService {

	@Autowired
	@Qualifier("productoRepository")
	private ProductoRepository productoRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Producto org) {
		org.setDetalleCompra(null);
		org.setDetalleVenta(null);
		org.getSubGrupo().getGrupo().setSubGrupo(null);
		org.getSubGrupo().setProducto(null);
	}

	public List<Producto> clearListLazyVariables(List<Producto> orgs) {
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
	public List<Producto> findAll() {
		List<Producto> producto = (List<Producto>) productoRepository.findAll();
		if (producto.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(producto);
		return producto;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return producto: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Producto> findById(Long id) {
		Optional<Producto> producto = productoRepository.findById(id);
		if (!producto.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(producto.get());
		return producto;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param producto: Contiene todos campos de la entidad para guardar
	 * @return Producto: La entidad Guardada
	 */
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 */
	public void delete(Long id) {
		productoRepository.deleteById(id);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Producto update(Producto newProducto) {
		Optional<Producto> oldProducto = findById(newProducto.getProd_id());
		copyNonNullProperties(newProducto, oldProducto.get());
		return productoRepository.save(oldProducto.get());
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
