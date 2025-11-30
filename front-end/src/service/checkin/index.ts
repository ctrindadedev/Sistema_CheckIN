import type { Inscricao } from "../../types";

const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

class InscricaoService {
  private static instance: InscricaoService;
  private inscricoes: Inscricao[] = [];
  private sequence = 1;

  private constructor() {}

  static getInstance(): InscricaoService {
    if (!InscricaoService.instance) {
      InscricaoService.instance = new InscricaoService();
    }
    return InscricaoService.instance;
  }

  async realizarCheckin(
    eventoId: number,
    usuarioId: number
  ): Promise<Inscricao> {
    await delay(500);

    const jaInscrito = this.inscricoes.some(
      (inscricao) =>
        inscricao.eventoId === eventoId && inscricao.usuarioId === usuarioId
    );

    if (jaInscrito) {
      throw new Error("Você já confirmou presença neste evento.");
    }

    const novaInscricao: Inscricao = {
      id: this.sequence++,
      eventoId,
      usuarioId,
      dataCheckin: new Date().toISOString(),
    };

    this.inscricoes.push(novaInscricao);
    return novaInscricao;
  }

  async cancelarInscricao(inscricaoId: number): Promise<void> {
    await delay(300);
    this.inscricoes = this.inscricoes.filter(
      (inscricao) => inscricao.id !== inscricaoId
    );
  }

  async listarPorUsuario(usuarioId: number): Promise<Inscricao[]> {
    await delay(300);
    return this.inscricoes.filter(
      (inscricao) => inscricao.usuarioId === usuarioId
    );
  }

  async listarTodas(): Promise<Inscricao[]> {
    await delay(300);
    return [...this.inscricoes];
  }
}

export const inscricaoService = InscricaoService.getInstance();
