import type { LoginPayload, Usuario } from "../../types";

const STORAGE_KEY = "@checkin-system:user";

const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

class AuthService {
  private static instance: AuthService;
  private currentUser: Usuario | null = null;

  private constructor() {
    if (typeof window !== "undefined") {
      const stored = window.localStorage.getItem(STORAGE_KEY);
      if (stored) {
        this.currentUser = JSON.parse(stored);
      }
    }
  }

  static getInstance(): AuthService {
    if (!AuthService.instance) {
      AuthService.instance = new AuthService();
    }
    return AuthService.instance;
  }

  async login(payload: LoginPayload): Promise<Usuario> {
    await delay(600);

    const token =
      typeof window !== "undefined" && window.crypto?.randomUUID
        ? window.crypto.randomUUID()
        : Math.random().toString(36).slice(2);

    const user: Usuario = {
      id: Date.now(),
      nome: "Participante Convidado",
      email: payload.email,
      token,
    };

    this.currentUser = user;
    if (typeof window !== "undefined") {
      window.localStorage.setItem(STORAGE_KEY, JSON.stringify(user));
    }

    return user;
  }

  async logout(): Promise<void> {
    await delay(200);
    this.currentUser = null;
    if (typeof window !== "undefined") {
      window.localStorage.removeItem(STORAGE_KEY);
    }
  }

  getCurrentUser(): Usuario | null {
    return this.currentUser;
  }
}

export const authService = AuthService.getInstance();
