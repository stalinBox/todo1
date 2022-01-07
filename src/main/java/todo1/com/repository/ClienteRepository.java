package todo1.com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import todo1.com.domain.Cliente;

@Repository("clienteRepository")
@Transactional
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
