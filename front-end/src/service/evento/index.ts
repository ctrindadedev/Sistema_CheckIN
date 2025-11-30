//Crud de eventos (apenas uma pequena simulação enquanto não tem a API )
import type { Evento } from "../../types";
const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

const EVENTOS_MOCK: Evento[] = [
  {
    id: 1,
    titulo: "Semana de Tecnologia",
    data: "23-11-2025",
    local: "IMD",
    descricao: "...",
    vagas: 50,
    imagemUrl: "https://images.unsplash.com/photo-1518770660439-4636190af475",
  },
  {
    id: 2,
    titulo: "Workshop React",
    data: "23-11-2025",
    local: "DIMAP",
    descricao: "...",
    vagas: 20,
    imagemUrl: "https://images.unsplash.com/photo-1518770660439-4636190af475",
  },
];

//MUDAR para singleton quando continuar o desenvolvimento

class EventoService {
  private static instance: EventoService;
  private eventos: Evento[] = [...EVENTOS_MOCK];

  private constructor() {}

  static getInstance(): EventoService {
    if (!EventoService.instance) {
      EventoService.instance = new EventoService();
    }
    return EventoService.instance;
  }

  async listarTodos(): Promise<Evento[]> {
    await delay(500);
    return [...this.eventos];
  }

  async buscarPorId(id: number): Promise<Evento> {
    await delay(200);
    const evento = this.eventos.find((item) => item.id === id);
    if (!evento) {
      throw new Error("Evento não encontrado");
    }
    return evento;
  }

  async criar(evento: Omit<Evento, "id">): Promise<Evento> {
    await delay(400);
    const novoEvento: Evento = {
      ...evento,
      id: Date.now(),
    };
    this.eventos.push(novoEvento);
    return novoEvento;
  }

  async atualizar(id: number, dados: Partial<Evento>): Promise<Evento> {
    await delay(400);
    const index = this.eventos.findIndex((item) => item.id === id);
    if (index === -1) {
      throw new Error("Evento não encontrado");
    }
    this.eventos[index] = { ...this.eventos[index], ...dados };
    return this.eventos[index];
  }

  async remover(id: number): Promise<void> {
    await delay(200);
    this.eventos = this.eventos.filter((item) => item.id !== id);
  }
}

export const eventoService = EventoService.getInstance();
