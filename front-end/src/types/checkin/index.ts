import type { InscricaoResponse } from "../inscricao";

export interface CheckinRequest {
  eventoId: number;
  usuarioId: number;
}

export type CheckinResponse = InscricaoResponse;
