package grupoHeitorECaio.checkInProject.repository;

import grupoHeitorECaio.checkInProject.model.Evento;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventoRepository {

    // O "banco de Dados" em memória
    private final Map<Long, Evento> eventos = new HashMap<>();
    // gerador de ids sequenciais
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Evento save(Evento evento) {
        if (evento.getId() == null) {
            evento.setId(idGenerator.getAndIncrement());
        }
        eventos.put(evento.getId(), evento);
        return evento;
    }

    public List<Evento> findAll() {
        return new ArrayList<>(eventos.values());
    }

    public Optional<Evento> findById(Long id) {
        return Optional.ofNullable(eventos.get(id));
    }

    public void deleteById(Long id) {
        eventos.remove(id);
    }
}