package ec.gob.mag.schema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.schema.domain.Officer;

@Repository("officerRepository")
public interface OfficerRepository extends CrudRepository<Officer, Long> {

	List<Officer> findByCampoEliminadoAndCampoEstadoEquals(boolean campoEliminado, Integer campoEstado);

	Optional<Officer> findByIdAndCampoEliminadoAndCampoEstadoEquals(Long id, boolean campoEliminado,
			Integer campoEstado);
}
