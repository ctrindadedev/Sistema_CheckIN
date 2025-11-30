import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { useAuth } from "../context/AuthContext";
import { useEvents } from "../context/EventContext";
import { inscricaoService } from "../service/checkin";
import type { Inscricao } from "../types";

const Container = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const Card = styled.article`
  background: ${({ theme }) => theme.colors.surface};
  border-radius: ${({ theme }) => theme.radii.md};
  padding: 1.5rem;
  box-shadow: 0 15px 35px rgba(15, 23, 42, 0.08);
`;

const Placeholder = styled.div`
  text-align: center;
  padding: 2rem;
  background: ${({ theme }) => theme.colors.surface};
  border-radius: ${({ theme }) => theme.radii.md};
  color: ${({ theme }) => theme.colors.muted};
`;

const EventoUser = () => {
  const { isAuthenticated, user } = useAuth();
  const { getEventById } = useEvents();
  const [inscricoes, setInscricoes] = useState<Inscricao[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!user) return;

    setLoading(true);
    inscricaoService
      .listarPorUsuario(user.id)
      .then(setInscricoes)
      .finally(() => setLoading(false));
  }, [user]);

  if (!isAuthenticated) {
    return (
      <Placeholder>
        Entre na plataforma para visualizar seus ingressos.{" "}
        <Link to="/auth">Fazer login</Link>
      </Placeholder>
    );
  }

  if (loading) {
    return <Placeholder>Carregando suas inscrições...</Placeholder>;
  }

  if (inscricoes.length === 0) {
    return (
      <Placeholder>
        Você ainda não confirmou presença em nenhum evento.{" "}
        <Link to="/">Descobrir eventos</Link>
      </Placeholder>
    );
  }

  return (
    <Container>
      {inscricoes.map((inscricao) => {
        const evento = getEventById(inscricao.eventoId);
        if (!evento) return null;

        return (
          <Card key={inscricao.id}>
            <h3>{evento.titulo}</h3>
            <p>{evento.local}</p>
            <small>
              Check-in em{" "}
              {new Date(inscricao.dataCheckin).toLocaleString("pt-BR")}
            </small>
            <Link to={`/evento/${evento.id}`}>Ver detalhes</Link>
          </Card>
        );
      })}
    </Container>
  );
};

export default EventoUser;
