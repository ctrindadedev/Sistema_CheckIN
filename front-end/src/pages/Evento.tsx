import React, { useState } from "react";
import { Link, useParams } from "react-router-dom";
import styled from "styled-components";
import EventoItem from "../components/EventoItem";
import ModalCheckIn from "../components/ModalCheckIn";
import { useEvents } from "../context/EventContext";
import { useAuth } from "../context/AuthContext";
import { inscricaoService } from "../service/checkin";

const Container = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const Actions = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-items: center;
`;

const PrimaryButton = styled.button`
  border: none;
  padding: 0.9rem 1.4rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  background: ${({ theme }) => theme.colors.primary};
  color: #fff;
  font-weight: 600;
`;

const SecondaryLink = styled(Link)`
  color: ${({ theme }) => theme.colors.primary};
  font-weight: 600;
`;

const Placeholder = styled.p`
  color: ${({ theme }) => theme.colors.muted};
`;

const Evento = () => {
  const { id } = useParams<{ id: string }>();
  const eventId = Number(id);
  const { getEventById, isLoading } = useEvents();
  const evento = Number.isFinite(eventId) ? getEventById(eventId) : undefined;
  const { isAuthenticated, user } = useAuth();

  const [modalOpen, setModalOpen] = useState(false);
  const [isConfirming, setIsConfirming] = useState(false);
  const [feedback, setFeedback] = useState<{
    status: "success" | "error";
    message: string;
  } | null>(null);

  const handleConfirmCheckin = async () => {
    if (!evento || !user) {
      setFeedback({
        status: "error",
        message: "Você precisa estar logado para confirmar.",
      });
      return;
    }

    try {
      setIsConfirming(true);
      await inscricaoService.realizarCheckin(evento.id, user.id);
      setFeedback({
        status: "success",
        message: "Check-in reservado! Veja em 'Meus eventos'.",
      });
    } catch (error) {
      const message =
        error instanceof Error ? error.message : "Não foi possível confirmar.";
      setFeedback({ status: "error", message });
    } finally {
      setIsConfirming(false);
    }
  };

  if (isLoading) {
    return <Placeholder>Carregando evento...</Placeholder>;
  }

  if (!evento) {
    return <Placeholder>Evento não encontrado.</Placeholder>;
  }

  return (
    <Container>
      <EventoItem evento={evento} />
      <Actions>
        {isAuthenticated ? (
          <PrimaryButton
            onClick={() => {
              setFeedback(null);
              setModalOpen(true);
            }}
          >
            Confirmar presença
          </PrimaryButton>
        ) : (
          <SecondaryLink to="/auth">
            Entre para confirmar sua presença
          </SecondaryLink>
        )}
        <SecondaryLink to="/">← Voltar</SecondaryLink>
      </Actions>

      <ModalCheckIn
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        onConfirm={handleConfirmCheckin}
        isLoading={isConfirming}
        feedback={feedback}
      />
    </Container>
  );
};

export default Evento;
