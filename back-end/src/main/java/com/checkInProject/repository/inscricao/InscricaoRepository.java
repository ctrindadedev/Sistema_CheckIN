package com.checkInProject.repository.inscricao;
import com.checkInProject.model.Evento;
import com.checkInProject.model.Inscricao;
import com.checkInProject.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    int countByEvento(Evento evento);

    Optional<Inscricao> findByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);

    List<Inscricao> findByUsuario(Usuario usuario);

    boolean existsByEventoAndUsuario(Evento evento, Usuario usuario);
}