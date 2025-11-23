//Crud de eventos (apenas uma pequena simulação enquanto não tem a API )
import type { Evento } from "../../types";

const EVENTOS_MOCK: Evento[] = [
  {
    id: 1,
    titulo: "Semana de Tecnologia",
    data: "23-11-2025",
    local: "IMD",
    descricao: "...",
    vagas: 50,
  },
  {
    id: 2,
    titulo: "Workshop React",
    data: "23-11-2025",
    local: "DIMAP",
    descricao: "...",
    vagas: 20,
  },
];

//MUDAR para singleton quando continuar o desenvolvimento

export const EventoService = {
  listarTodos: async (): Promise<Evento[]> => {
    return new Promise((resolve) =>
      setTimeout(() => resolve(EVENTOS_MOCK), 500)
    );
  },
};

