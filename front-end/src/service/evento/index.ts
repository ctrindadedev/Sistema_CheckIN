import { GenericService } from "../GenericService";
import type { EventoRequest, EventoResponse } from "../../types";

class EventoService extends GenericService<EventoRequest, EventoResponse> {
  private static instance: EventoService;

  private constructor() {
    super("/eventos");
  }

  public static getInstance(): EventoService {
    if (!EventoService.instance) {
      EventoService.instance = new EventoService();
    }
    return EventoService.instance;
  }
}

export const eventoService = EventoService.getInstance();
