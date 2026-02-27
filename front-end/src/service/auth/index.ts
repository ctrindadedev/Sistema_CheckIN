import { api } from "../api";
import { jwtDecode } from "jwt-decode";
import type { LoginRequest, LoginResponse, JWTUserData } from "../../types";

class AuthService {
  async login(payload: LoginRequest): Promise<JWTUserData> {
    const response = await api.post<LoginResponse>("/auth/login", payload);
    const token = response.data.token;
    localStorage.setItem("token", token);

    return jwtDecode<JWTUserData>(token);
  }

  logout(): void {
    localStorage.removeItem("token");
  }

  //Usa quando a página recarrega para verificar se ainda tem o JWT
  getCurrentUser(): JWTUserData | null {
    const token = localStorage.getItem("token");
    if (!token) return null;

    try {
      const decoded = jwtDecode<JWTUserData>(token);

      if (decoded.exp && decoded.exp * 1000 < Date.now()) {
        this.logout();
        return null;
      }

      return decoded;
    } catch (error) {
      this.logout();
      return null;
    }
  }
}

export const authService = new AuthService();
