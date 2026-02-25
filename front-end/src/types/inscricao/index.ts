export interface InscricaoRequest {
  eventoId: number;
}

export interface InscricaoResponse {
  id: number;
  eventoId: number;
  usuarioId: number;
  nomeUsuario: string;
  nomeEvento: string;
  status: "AGUARDANDO_VALIDACAO" | "VALIDADO";
  dataCheckin: string | null;
}
