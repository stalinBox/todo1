package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.gob.mag.domain.Noticias;

@Repository("noticiasRepository")
@Transactional
public interface NoticiasRepository extends CrudRepository<Noticias, Long> {

	List<Noticias> findByNotEliminadoAndNotEstadoEquals(boolean notEliminado, Integer notEstado);

	Optional<Noticias> findByNotIdAndNotEliminadoAndNotEstadoEquals(Long id, boolean notEliminado, Integer notEstado);

}
