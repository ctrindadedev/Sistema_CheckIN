import { api } from "./api";
import type { AxiosResponse } from "axios";

export class GenericService<TRequest, TResponse> {
  protected endpoint: string;

  // protected permite que as classes filhas chamem o super(), mas impede que instanciem BaseService diretamente
  protected constructor(endpoint: string) {
    this.endpoint = endpoint;
  }

  async listarTodos(): Promise<TResponse[]> {
    const response: AxiosResponse<TResponse[]> = await api.get(this.endpoint);
    return response.data;
  }

  async buscarPorId(id: number): Promise<TResponse> {
    const response: AxiosResponse<TResponse> = await api.get(
      `${this.endpoint}/${id}`,
    );
    return response.data;
  }

  async criar(dados: TRequest): Promise<TResponse> {
    const response: AxiosResponse<TResponse> = await api.post(
      this.endpoint,
      dados,
    );
    return response.data;
  }

  async atualizar(id: number, dados: TRequest): Promise<TResponse> {
    const response: AxiosResponse<TResponse> = await api.put(
      `${this.endpoint}/${id}`,
      dados,
    );
    return response.data;
  }

  async remover(id: number): Promise<void> {
    await api.delete(`${this.endpoint}/${id}`);
  }
}
