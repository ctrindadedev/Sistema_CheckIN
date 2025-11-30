import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
  type ReactNode,
} from "react";
import type { Evento } from "../types";
import { eventoService } from "../service/evento";

interface EventContextValue {
  events: Evento[];
  isLoading: boolean;
  error: string | null;
  refresh: () => Promise<void>;
  getEventById: (id: number) => Evento | undefined;
}

const EventContext = createContext<EventContextValue | undefined>(undefined);

export const EventProvider = ({ children }: { children: ReactNode }) => {
  const [events, setEvents] = useState<Evento[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const loadEvents = useCallback(async () => {
    setIsLoading(true);
    try {
      const lista = await eventoService.listarTodos();
      setEvents(lista);
      setError(null);
    } catch (err) {
      const message =
        err instanceof Error ? err.message : "Erro ao carregar eventos.";
      setError(message);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    void loadEvents();
  }, [loadEvents]);

  const getEventById = useCallback(
    (id: number) => events.find((evento) => evento.id === id),
    [events]
  );

  const value = useMemo<EventContextValue>(
    () => ({
      events,
      isLoading,
      error,
      refresh: loadEvents,
      getEventById,
    }),
    [events, error, getEventById, isLoading, loadEvents]
  );

  return (
    <EventContext.Provider value={value}>{children}</EventContext.Provider>
  );
};

export const useEvents = () => {
  const context = useContext(EventContext);
  if (!context) {
    throw new Error("useEvents deve ser utilizado dentro do EventProvider");
  }
  return context;
};
