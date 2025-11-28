package grupoHeitorECaio.checkInProject.controller;

import grupoHeitorECaio.checkInProject.model.Evento;
import grupoHeitorECaio.checkInProject.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos") // Bate com a pasta "evento" do service
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private EventoRepository repository;

    @GetMapping
    public List<Evento> listarEventos() {
        return repository.findAll();
    }

    @PostMapping
    public Evento criarEvento(@RequestBody Evento evento) {
        return repository.save(evento);
    }

    @GetMapping("/{id}")
    public Evento buscarPorId(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}