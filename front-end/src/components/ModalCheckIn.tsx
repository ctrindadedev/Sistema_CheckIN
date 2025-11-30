import React from "react";
import styled from "styled-components";

const Overlay = styled.div`
  position: fixed;
  inset: 0;
  background: rgba(15, 23, 42, 0.55);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 30;
`;

const Dialog = styled.div`
  width: min(420px, 90%);
  background: ${({ theme }) => theme.colors.surface};
  border-radius: ${({ theme }) => theme.radii.md};
  padding: 2rem;
  box-shadow: 0 20px 50px rgba(15, 23, 42, 0.35);
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Title = styled.h3`
  font-size: 1.3rem;
`;

const Description = styled.p`
  color: ${({ theme }) => theme.colors.muted};
  line-height: 1.5;
`;

const Actions = styled.div`
  margin-top: 0.5rem;
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
`;

const Button = styled.button<{ $variant?: "outline" | "solid" }>`
  flex: 1;
  padding: 0.75rem 1rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  border: ${({ $variant }) =>
    $variant === "outline" ? "1px solid rgba(15, 23, 42, 0.15)" : "none"};
  background: ${({ theme, $variant }) =>
    $variant === "outline" ? "transparent" : theme.colors.primary};
  color: ${({ theme, $variant }) =>
    $variant === "outline" ? theme.colors.muted : "#fff"};
  font-weight: 600;
  transition: opacity 150ms ease;

  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
`;

const Feedback = styled.small<{ $status: "success" | "error" }>`
  color: ${({ $status }) => ($status === "success" ? "#16a34a" : "#dc2626")};
`;

interface ModalCheckInProps {
  open: boolean;
  onClose: () => void;
  onConfirm: () => Promise<void> | void;
  isLoading?: boolean;
  feedback?: { status: "success" | "error"; message: string } | null;
}

const ModalCheckIn: React.FC<ModalCheckInProps> = ({
  open,
  onClose,
  onConfirm,
  isLoading = false,
  feedback,
}) => {
  if (!open) return null;

  const handleConfirm = () => {
    void onConfirm();
  };

  return (
    <Overlay>
      <Dialog>
        <Title>Confirmar presença</Title>
        <Description>
          Ao confirmar, você garante sua vaga para o evento. Utilize o mesmo
          e-mail na recepção para validar o ingresso.
        </Description>

        {feedback && (
          <Feedback $status={feedback.status}>{feedback.message}</Feedback>
        )}

        <Actions>
          <Button $variant="outline" onClick={onClose} disabled={isLoading}>
            Cancelar
          </Button>
          <Button onClick={handleConfirm} disabled={isLoading}>
            {isLoading ? "Confirmando..." : "Confirmar presença"}
          </Button>
        </Actions>
      </Dialog>
    </Overlay>
  );
};

export default ModalCheckIn;
