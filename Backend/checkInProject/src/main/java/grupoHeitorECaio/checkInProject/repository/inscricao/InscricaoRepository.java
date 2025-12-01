package grupoHeitorECaio.checkInProject.repository.inscricao;

import grupoHeitorECaio.checkInProject.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    long countByEventoId(Long eventoId);

    boolean existsByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);

    Optional<Inscricao> findByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);

    List<Inscricao> findByUsuarioId(Long usuarioId);
}

