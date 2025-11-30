import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import type { Evento } from "../types";

const Card = styled.article`
  background: ${({ theme }) => theme.colors.surface};
  border-radius: ${({ theme }) => theme.radii.md};
  padding: 1.5rem;
  box-shadow: 0 15px 35px rgba(15, 23, 42, 0.08);
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

const Title = styled.h3`
  font-size: 1.2rem;
  color: ${({ theme }) => theme.colors.text};
`;

const Description = styled.p`
  color: ${({ theme }) => theme.colors.muted};
  line-height: 1.5;
  flex: 1;
`;

const Meta = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
  font-size: 0.95rem;
  color: ${({ theme }) => theme.colors.muted};
`;

const Action = styled(Link)`
  margin-top: 0.5rem;
  align-self: flex-start;
  padding: 0.65rem 1.1rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  background: ${({ theme }) => theme.colors.secondary};
  color: #fff;
  font-weight: 600;
`;

interface HomeEventoCardProps {
  evento: Evento;
}

const formatDate = (raw: string) => {
  const date = new Date(raw);
  return Intl.DateTimeFormat("pt-BR", {
    day: "2-digit",
    month: "short",
    hour: "2-digit",
    minute: "2-digit",
  }).format(date);
};

const HomeEventoCard: React.FC<HomeEventoCardProps> = ({ evento }) => {
  return (
    <Card>
      <Title>{evento.titulo}</Title>
      <Meta>
        <span>{evento.data}</span>
        <span>{evento.local}</span>
        <span>{evento.vagas} vagas</span>
      </Meta>
      <Description>{evento.descricao}</Description>
      <Action to={`/evento/${evento.id}`}>Saber mais</Action>
    </Card>
  );
};

export default HomeEventoCard;
