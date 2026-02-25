export interface EventoRequest {
  titulo: string;
  descricao: string;
  data: string;
  local: string;
  vagas: number;
  imagemUrl?: string;
}

export interface EventoResponse {
  id: number;
  titulo: string;
  descricao: string;
  data: string;
  local: string;
  vagas: number;
  imagemUrl?: string;
  organizadorId: number;
  organizadorNome: string;
}
