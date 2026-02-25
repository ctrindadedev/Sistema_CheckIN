export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  role?: "PARTICIPANTE" | "ORGANIZADOR";
}

export interface RegisterResponse {
  name: string;
  email: string;
}

export interface JWTUserData {
  userId: number;
  email: string;
  roles: string[];
  sub?: string; // subject
  iss?: number; // issued at
  exp?: number; // expiration
}
