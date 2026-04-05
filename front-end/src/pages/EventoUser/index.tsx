import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { useQuery } from "@tanstack/react-query";
import { useAuth } from "../../context/AuthContext";
import { inscricaoService } from "../../service/inscricao";

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
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const Placeholder = styled.div`
  text-align: center;
  padding: 2rem;
  background: ${({ theme }) => theme.colors.surface};
  border-radius: ${({ theme }) => theme.radii.md};
  color: ${({ theme }) => theme.colors.muted};

  a {
    display: inline-block;
    margin-top: 0.75rem;
    color: ${({ theme }) => theme.colors.primary};
    font-weight: 600;
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }
`;

const EventoUser = () => {
  const { isAuthenticated, user } = useAuth();

  const userId = user?.id || user?.userId;

  const { data: inscricoes = [], isLoading } = useQuery({
    queryKey: ["inscricoes", userId],
    queryFn: () => inscricaoService.listarPorUsuario(userId as number),
    enabled: !!userId,
  });

  if (!isAuthenticated) {
    return (
      <Placeholder>
        Entre na plataforma para visualizar seus ingressos.
        <Link to="/auth">Fazer login</Link>
      </Placeholder>
    );
  }

  if (isLoading) {
    return <Placeholder>Carregando suas inscrições...</Placeholder>;
  }

  if (inscricoes.length === 0) {
    return (
      <Placeholder>
        Você ainda não confirmou presença em nenhum evento.
        <Link to="/">Descobrir eventos</Link>
      </Placeholder>
    );
  }

  return (
    <Container>
      {inscricoes.map((inscricao) => (
        <Card key={inscricao.id}>
          <h3>{inscricao.nomeEvento}</h3>
          <p>
            Status:{" "}
            <strong>
              {inscricao.status === "VALIDADO"
                ? "Presente"
                : "Aguardando Checkin"}
            </strong>
          </p>

          {inscricao.dataCheckin && (
            <small>
              Check-in em:{" "}
              {new Date(inscricao.dataCheckin).toLocaleString("pt-BR")}
            </small>
          )}

          <Link
            to={`/evento/${inscricao.eventoId}`}
            style={{ marginTop: "1rem", color: "#6366f1", fontWeight: 600 }}
          >
            Ver página do evento
          </Link>
        </Card>
      ))}
    </Container>
  );
};

export default EventoUser;
