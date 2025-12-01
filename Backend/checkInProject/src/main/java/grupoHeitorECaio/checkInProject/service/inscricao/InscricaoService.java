package grupoHeitorECaio.checkInProject.service.inscricao;

import grupoHeitorECaio.checkInProject.model.Evento;
import grupoHeitorECaio.checkInProject.model.Inscricao;
import grupoHeitorECaio.checkInProject.repository.inscricao.InscricaoRepository;
import grupoHeitorECaio.checkInProject.service.evento.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoService eventoService;

    public InscricaoService(InscricaoRepository inscricaoRepository,
                            EventoService eventoService) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoService = eventoService;
    }

    public List<Inscricao> listarPorUsuario(Long usuarioId) {
        return inscricaoRepository.findByUsuarioId(usuarioId);
    }

    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAll();
    }

    @Transactional
    public Inscricao realizarCheckin(Long eventoId, Long usuarioId) {
        Evento evento = eventoService.buscarPorId(eventoId);

        if (inscricaoRepository.existsByEventoIdAndUsuarioId(eventoId, usuarioId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já inscrito neste evento");
        }

        if (evento.getVagas() != null) {
            long ocupadas = inscricaoRepository.countByEventoId(eventoId);
            if (ocupadas >= evento.getVagas()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há vagas disponíveis");
            }
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setEventoId(eventoId);
        inscricao.setUsuarioId(usuarioId);
        inscricao.setDataCheckin(LocalDateTime.now());

        return inscricaoRepository.save(inscricao);
    }

    @Transactional
    public void cancelarInscricao(Long inscricaoId) {
        Inscricao existente = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada"));
        inscricaoRepository.delete(existente);
    }

    public boolean possuiInscricao(Long eventoId, Long usuarioId) {
        return inscricaoRepository.existsByEventoIdAndUsuarioId(eventoId, usuarioId);
    }
}

