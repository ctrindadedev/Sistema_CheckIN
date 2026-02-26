import React, { useState } from "react";
import { Link, useParams } from "react-router-dom";
import styled from "styled-components";
import { useQuery } from "@tanstack/react-query";
import { EventoItem } from "../../components/Eventos/EventoItem";
import ModalInscricao from "../../components/Inscricao/ModalInscricao";
import { useAuth } from "../../context/AuthContext";
import { eventoService } from "../../service/evento";
import { inscricaoService } from "../../service/inscricao";

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
  cursor: pointer;
  transition: opacity 0.2s;

  &:hover {
    opacity: 0.9;
  }
`;

const SecondaryLink = styled(Link)`
  color: ${({ theme }) => theme.colors.primary};
  font-weight: 600;
  text-decoration: none;

  &:hover {
    text-decoration: underline;
  }
`;

const Placeholder = styled.p`
  color: ${({ theme }) => theme.colors.muted};
`;

const Evento = () => {
  const { id } = useParams<{ id: string }>();
  const eventId = Number(id);
  const { isAuthenticated } = useAuth();

  const {
    data: evento,
    isLoading,
    error,
  } = useQuery({
    queryKey: ["evento", eventId],
    queryFn: () => eventoService.buscarPorId(eventId),
    enabled: Number.isFinite(eventId),
  });

  const [modalOpen, setModalOpen] = useState(false);
  const [isConfirming, setIsConfirming] = useState(false);
  const [feedback, setFeedback] = useState<{
    status: "success" | "error";
    message: string;
  } | null>(null);

  const handleConfirmInscricao = async () => {
    if (!evento) return;
    try {
      setIsConfirming(true);
      await inscricaoService.criar({ eventoId: evento.id });

      setFeedback({
        status: "success",
        message: "Inscrição realizada! Veja em 'Meus eventos'.",
      });
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message ||
        "Não foi possível confirmar a inscrição.";
      setFeedback({ status: "error", message: errorMessage });
    } finally {
      setIsConfirming(false);
    }
  };

  if (isLoading) {
    return <Placeholder>Carregando detalhes do evento...</Placeholder>;
  }

  if (!evento) {
    return <Placeholder>Evento não encontrado </Placeholder>;
  }

  if (error) {
    return <Placeholder> Ocorreu um erro ao buscar o evento. </Placeholder>;
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
            Participar do Evento
          </PrimaryButton>
        ) : (
          <SecondaryLink to="/auth">
            Entre para confirmar sua presença
          </SecondaryLink>
        )}
        <SecondaryLink to="/">← Voltar para listagem</SecondaryLink>
      </Actions>

      <ModalInscricao
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        onConfirm={handleConfirmInscricao}
        isLoading={isConfirming}
        feedback={feedback}
      />
    </Container>
  );
};

export default Evento;
