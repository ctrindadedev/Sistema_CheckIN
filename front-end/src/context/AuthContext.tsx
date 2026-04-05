import {
  createContext,
  useCallback,
  useContext,
  useMemo,
  useState,
  type ReactNode,
} from "react";
import { authService } from "../service/auth";
import type { LoginRequest, RegisterRequest, JWTUserData } from "../types";

interface AuthContextValue {
  user: JWTUserData | null;
  isAuthenticated: boolean;
  login: (payload: LoginRequest) => Promise<void>;
  register: (payload: RegisterRequest) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<JWTUserData | null>(
    authService.getCurrentUser(),
  );

  const login = useCallback(async (payload: LoginRequest) => {
    const userData = await authService.login(payload);
    setUser(userData);
  }, []);

  const register = useCallback(async (payload: RegisterRequest) => {
    await authService.register(payload);
  }, []);

  const logout = useCallback(() => {
    authService.logout();
    setUser(null);
  }, []);

  const value = useMemo<AuthContextValue>(
    () => ({
      user,
      isAuthenticated: Boolean(user),
      login,
      register,
      logout,
    }),
    [login, register, logout, user],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth deve ser utilizado dentro do AuthProvider");
  }
  return context;
};
