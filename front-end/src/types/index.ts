export interface Usuario {
  id: number;
  nome: string;
  email: string;
  token?: string;
}

export interface Evento {
  id: number;
  titulo: string;
  descricao: string;
  data: string;
  local: string;
  vagas: number;
  imagemUrl?: string;
}

export interface Inscricao {
  id: number;
  eventoId: number;
  usuarioId: number;
  dataCheckin: string;
}

export interface LoginPayload {
  email: string;
  senha: string;
}

export interface CheckinPayload {
  eventoId: number;
  usuarioId: number;
}
