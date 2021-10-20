package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.gob.mag.domain.Cumpleanios;

@Repository("cumpleaniosRepository")
@Transactional
public interface CumpleaniosRepository extends CrudRepository<Cumpleanios, Long> {

	List<Cumpleanios> findByCumEliminadoAndCumEstadoEquals(boolean cumEliminado, Integer cumEstado);

	Optional<Cumpleanios> findByCumIdAndCumEliminadoAndCumEstadoEquals(Long id, boolean cumEliminado,
			Integer cumEstado);

}
