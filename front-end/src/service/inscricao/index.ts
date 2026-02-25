import { GenericService } from "../GenericService";
import { api } from "../api";
import type { InscricaoRequest, InscricaoResponse } from "../../types";

class InscricaoService extends GenericService<
  InscricaoRequest,
  InscricaoResponse
> {
  private static instance: InscricaoService;

  private constructor() {
    super("/inscricao");
  }

  public static getInstance(): InscricaoService {
    if (!InscricaoService.instance) {
      InscricaoService.instance = new InscricaoService();
    }
    return InscricaoService.instance;
  }

  async listarPorUsuario(usuarioId: number): Promise<InscricaoResponse[]> {
    const response = await api.get<InscricaoResponse[]>(
      `${this.endpoint}/usuario/${usuarioId}`,
    );
    return response.data;
  }
}

export const inscricaoService = InscricaoService.getInstance();
