import { api } from "../api";
import type { CheckinRequest, CheckinResponse } from "../../types";

class CheckinService {
  private static instance: CheckinService;
  private constructor() {}

  public static getInstance(): CheckinService {
    if (!CheckinService.instance) {
      CheckinService.instance = new CheckinService();
    }
    return CheckinService.instance;
  }
  async realizarCheckin(dados: CheckinRequest): Promise<CheckinResponse> {
    const response = await api.post<CheckinResponse>("/checkin", dados);
    return response.data;
  }

  async cancelarCheckin(dados: CheckinRequest): Promise<void> {
    await api.delete("/checkin", { data: dados });
  }
}

export const checkinService = CheckinService.getInstance();
