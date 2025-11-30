import React from "react";
import styled from "styled-components";
import type { Evento } from "../types";

const Wrapper = styled.article`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
  align-items: center;
  background: ${({ theme }) => theme.colors.surface};
  border-radius: ${({ theme }) => theme.radii.md};
  box-shadow: 0 20px 45px rgba(15, 23, 42, 0.12);
  padding: 2rem;
`;

const Cover = styled.div<{ $image?: string }>`
  width: 100%;
  height: 260px;
  border-radius: ${({ theme }) => theme.radii.md};
  background: ${({ $image }) =>
    $image
      ? `url(${$image}) center/cover no-repeat`
      : "linear-gradient(130deg, #6366f1, #14b8a6)"};
`;

const Title = styled.h1`
  font-size: clamp(1.8rem, 3vw, 2.6rem);
  color: ${({ theme }) => theme.colors.text};
  margin-bottom: 0.5rem;
`;

const Description = styled.p`
  color: ${({ theme }) => theme.colors.muted};
  line-height: 1.6;
`;

const MetaGrid = styled.div`
  margin-top: 1.5rem;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  gap: 1rem;
`;

const MetaBlock = styled.div`
  padding: 1rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  background: rgba(79, 70, 229, 0.08);
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
  color: ${({ theme }) => theme.colors.text};
`;

const Label = styled.span`
  font-size: 0.8rem;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  color: ${({ theme }) => theme.colors.muted};
`;

const Value = styled.strong`
  font-size: 1rem;
`;

interface EventoItemProps {
  evento: Evento;
}

const formatDate = (raw: string) => {
  const date = new Date(raw);
  return Intl.DateTimeFormat("pt-BR", {
    weekday: "long",
    day: "2-digit",
    month: "long",
    hour: "2-digit",
    minute: "2-digit",
  }).format(date);
};

const EventoItem: React.FC<EventoItemProps> = ({ evento }) => {
  return (
    <Wrapper>
      <Cover $image={evento.imagemUrl} role="presentation" />
      <div>
        <Title>{evento.titulo}</Title>
        <Description>{evento.descricao}</Description>
        <MetaGrid>
          <MetaBlock>
            <Label>Data</Label>
            <Value>{evento.data}</Value>
          </MetaBlock>
          <MetaBlock>
            <Label>Local</Label>
            <Value>{evento.local}</Value>
          </MetaBlock>
          <MetaBlock>
            <Label>Vagas</Label>
            <Value>{evento.vagas}</Value>
          </MetaBlock>
        </MetaGrid>
      </div>
    </Wrapper>
  );
};

export default EventoItem;
