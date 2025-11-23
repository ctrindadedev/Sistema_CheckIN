//Crud do checkin (apenas uma pequena simulação enquanto não tem a API )
//mudar para singleton

import type { Usuario } from "../../types";
import type { Evento } from "../../types";

export const InscricaoService = {
  realizarCheckin: async (
    eventoId: number,
    usuarioId: number
  ): Promise<boolean> => {
    console.log(
      `Check-in realizado: Usuário ${usuarioId} no Evento ${eventoId}`
    );
    return true;
  },

  cancelarInscricao(): async;

  getMinhasInscricoes(): async;

  verficarStatusInscricao(): async;
};
