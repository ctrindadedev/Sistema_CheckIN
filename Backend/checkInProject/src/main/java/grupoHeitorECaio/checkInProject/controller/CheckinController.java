package grupoHeitorECaio.checkInProject.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/checkin") // Bate com a pasta "checkin" do service
@CrossOrigin(origins = "*")
public class CheckinController {

    @PostMapping
    public Map<String, String> realizarCheckin(@RequestBody Map<String, Long> dadosCheckin) {
        Long usuarioId = dadosCheckin.get("usuarioId");
        Long eventoId = dadosCheckin.get("eventoId");

        // Lógica: Buscar evento no repository e adicionar o usuarioId na lista de
        // inscritos
        System.out.println("Check-in realizado! Usuário: " + usuarioId + " no Evento: " + eventoId);

        return Map.of("message", "Check-in realizado com sucesso!");
    }
}