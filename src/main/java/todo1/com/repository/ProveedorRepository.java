package todo1.com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import todo1.com.domain.Proveedor;

@Repository("proveedorRepository")
@Transactional
public interface ProveedorRepository extends CrudRepository<Proveedor, Long> {

}
