package todo1.com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import todo1.com.domain.SubGrupo;

@Repository("subGrupoRepository")
@Transactional
public interface SubGrupoRepository extends CrudRepository<SubGrupo, Long> {

}
