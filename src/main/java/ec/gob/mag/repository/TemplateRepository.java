package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.gob.mag.domain.Template;

@Repository("templateRepository")
@Transactional
public interface TemplateRepository extends CrudRepository<Template, Long> {

	List<Template> findByTmpEliminadoAndTmpEstadoEquals(boolean tmpEliminado, Integer tmpEstado);

	Optional<Template> findByIdAndTmpEliminadoAndTmpEstadoEquals(Long id, boolean tmpEliminado, Integer tmpEstado);

}
