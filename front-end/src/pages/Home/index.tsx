import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { useQuery } from "@tanstack/react-query";
import EventoHomeCard from "../../components/Eventos/EventoHomeCard";
import { eventoService } from "../../service/evento";

const Page = styled.section`
  display: flex;
  flex-direction: column;
  gap: 3rem;
`;

const Hero = styled.section`
  background: linear-gradient(130deg, #6366f1, #14b8a6);
  border-radius: ${({ theme }) => theme.radii.md};
  padding: clamp(2rem, 4vw, 3rem);
  color: #fff;
  display: grid;
  gap: 1rem;
`;

const HeroActions = styled.div`
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
`;

const HeroButton = styled(Link)`
  padding: 0.8rem 1.4rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  font-weight: 600;
  border: 1px solid rgba(255, 255, 255, 0.4);
  color: inherit;
  text-decoration: none;
  transition: background-color 0.2s;

  &:hover {
    background-color: rgba(255, 255, 255, 0.1);
  }
`;

const Grid = styled.div`
  display: grid;
  gap: 1.5rem;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
`;

const Placeholder = styled.div`
  text-align: center;
  color: ${({ theme }) => theme.colors.muted};
`;

const Home = () => {
  const {
    data: events = [],
    isLoading,
    error,
  } = useQuery({
    queryKey: ["eventos"],
    queryFn: () => eventoService.listarTodos(),
  });

  return (
    <Page>
      <Hero>
        <div>
          <p>Plataforma oficial de check-in</p>
          <h1>Descubra, cadastre-se e confirme presença em segundos.</h1>
        </div>
        <HeroActions>
          <HeroButton to="/auth">Quero participar</HeroButton>
          <HeroButton to="/meus-eventos">Meus eventos</HeroButton>
        </HeroActions>
      </Hero>

      {isLoading && <Placeholder>Carregando eventos...</Placeholder>}

      {error && (
        <Placeholder>Ocorreu um erro ao carregar os eventos.</Placeholder>
      )}

      {!isLoading && !error && events.length === 0 && (
        <Placeholder>
          Nenhum evento disponível por enquanto. Volte em breve!
        </Placeholder>
      )}

      {!isLoading && !error && events.length > 0 && (
        <Grid>
          {events.map((evento) => (
            <EventoHomeCard key={evento.id} evento={evento} />
          ))}
        </Grid>
      )}
    </Page>
  );
};

export default Home;
