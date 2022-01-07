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

import todo1.com.domain.Cliente;
import todo1.com.exception.MyNotFoundException;
import todo1.com.repository.ClienteRepository;

@Service("clienteService")
public class ClienteService {

	@Autowired
	@Qualifier("clienteRepository")
	private ClienteRepository clienteRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(Cliente org) {
		org.setVenta(null);
	}

	public List<Cliente> clearListLazyVariables(List<Cliente> orgs) {
		if (orgs != null)
			orgs = orgs.stream().map(u -> {
				clearObjectLazyVariables(u);
				return u;
			}).collect(Collectors.toList());
		return orgs;
	}

	/**
	 * Metodo para encontrar todos los registros de la tabla cliente
	 * 
	 * @return Todos los registros de la tabla
	 */
	public List<Cliente> findAll() {
		List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll();
		if (clientes.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(clientes);
		return clientes;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return cliente: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Cliente> findById(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (!cliente.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(cliente.get());
		return cliente;
	}

	/**
	 * Guarda un registro en la tabla cliente
	 * 
	 * @param cliente: Contiene todos campos de la entidad para guardar
	 * @return Cliente: La entidad Guardada
	 */
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	/**
	 * Elimina un registro por id
	 * 
	 * @param id: Identificador del registro
	 */
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param newCliente: Contiene todos campos de la entidad para guardar
	 * @return Cliente: La entidad Actualizada
	 */
	public Cliente update(Cliente newCliente) {
		Optional<Cliente> oldCliente = findById(newCliente.getCli_id());
		copyNonNullProperties(newCliente, oldCliente.get());
		return clienteRepository.save(oldCliente.get());
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
