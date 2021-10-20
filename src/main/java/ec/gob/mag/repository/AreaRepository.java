package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.gob.mag.domain.Area;

@Repository("areaRepository")
@Transactional
public interface AreaRepository extends CrudRepository<Area, Long> {

	List<Area> findByAreEliminadoAndAreEstadoEquals(boolean areEliminado, Integer areEstado);

	Optional<Area> findByAreIdAndAreEliminadoAndAreEstadoEquals(Long id, boolean areEliminado, Integer areEstado);

}
