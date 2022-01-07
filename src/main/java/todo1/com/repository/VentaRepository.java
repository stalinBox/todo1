package todo1.com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import todo1.com.domain.Venta;

@Repository("ventaRepository")
@Transactional
public interface VentaRepository extends CrudRepository<Venta, Long> {

}
