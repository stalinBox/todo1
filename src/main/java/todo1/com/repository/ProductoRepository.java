package todo1.com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import todo1.com.domain.Producto;

@Repository("productoRepository")
@Transactional
public interface ProductoRepository extends CrudRepository<Producto, Long> {

}
