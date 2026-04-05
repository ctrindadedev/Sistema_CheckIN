import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { Formik, Form, Field, ErrorMessage, type FormikHelpers } from "formik";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import * as Yup from "yup";
import { useAuth } from "../../context/AuthContext";
import { eventoService } from "../../service/evento";
import type { EventoRequest, EventoResponse } from "../../types";

const Page = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const Section = styled.div`
  background: ${({ theme }) => theme.colors.surface};
  border-radius: ${({ theme }) => theme.radii.md};
  padding: 2rem;
  box-shadow: 0 15px 35px rgba(15, 23, 42, 0.08);
`;

const SectionTitle = styled.h2`
  font-size: 1.3rem;
  margin-bottom: 1.5rem;
`;

const StyledForm = styled(Form)`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  max-width: 520px;
`;

const Label = styled.label`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  font-weight: 500;
  color: ${({ theme }) => theme.colors.text};
`;

const Input = styled(Field)`
  padding: 0.8rem 1rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  border: 1px solid rgba(15, 23, 42, 0.15);
  font-size: 1rem;
`;

const Textarea = styled(Field).attrs({ as: "textarea" })`
  padding: 0.8rem 1rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  border: 1px solid rgba(15, 23, 42, 0.15);
  font-size: 1rem;
  resize: vertical;
  min-height: 100px;
`;

const ErrorText = styled(ErrorMessage)`
  color: #dc2626;
  font-size: 0.85rem;
`;

const SubmitButton = styled.button`
  border: none;
  border-radius: ${({ theme }) => theme.radii.sm};
  padding: 0.85rem 1rem;
  background: ${({ theme }) => theme.colors.primary};
  color: #fff;
  font-weight: 600;
  transition: opacity 150ms ease;
  cursor: pointer;
  &:disabled {
    opacity: 0.65;
    cursor: not-allowed;
  }
`;

const StatusMessage = styled.small<{ $status: "success" | "error" }>`
  color: ${({ $status }) => ($status === "success" ? "#16a34a" : "#dc2626")};
  font-weight: 500;
`;

const EventList = styled.ul`
  list-style: none;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const EventCard = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  border: 1px solid rgba(15, 23, 42, 0.08);
  background: rgba(79, 70, 229, 0.04);
  gap: 1rem;

  @media (max-width: 640px) {
    flex-direction: column;
    align-items: flex-start;
  }
`;

const EventInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
`;

const EventName = styled.h3`
  font-size: 1rem;
  margin: 0;
`;

const EventMeta = styled.small`
  color: ${({ theme }) => theme.colors.muted};
`;

const EventActions = styled.div`
  display: flex;
  gap: 0.5rem;
`;

const IconBtn = styled.button<{ $variant?: "danger" }>`
  padding: 0.5rem 0.8rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  border: 1px solid ${({ $variant }) => ($variant === "danger" ? "#dc2626" : "rgba(15, 23, 42, 0.15)")};
  background: ${({ $variant }) => ($variant === "danger" ? "#dc2626" : "transparent")};
  color: ${({ $variant }) => ($variant === "danger" ? "#fff" : theme.colors.muted)};
  font-size: 0.85rem;
  font-weight: 500;
  cursor: pointer;
  transition: opacity 150ms ease;

  &:hover:not(:disabled) {
    opacity: 0.85;
  }

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
`;

const Placeholder = styled.p`
  color: ${({ theme }) => theme.colors.muted};
  text-align: center;
  padding: 2rem;
`;

const validationSchema = Yup.object<EventoRequest>({
  titulo: Yup.string()
    .min(3, "O título precisa ter ao menos 3 caracteres.")
    .required("O título é obrigatório."),
  descricao: Yup.string()
    .min(10, "A descrição precisa ter ao menos 10 caracteres.")
    .required("A descrição é obrigatória."),
  data: Yup.date()
    .required("A data do evento é obrigatória.")
    .min(new Date(), "A data não pode ser no passado."),
  local: Yup.string()
    .min(3, "O local precisa ter ao menos 3 caracteres.")
    .required("O local é obrigatório."),
  vagas: Yup.number()
    .integer("O número de vagas deve ser inteiro.")
    .min(1, "É necessário pelo menos 1 vaga.")
    .required("O número de vagas é obrigatório."),
  imagemUrl: Yup.string().url("URL de imagem inválida.").optional(),
});

const Admin = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [formStatus, setFormStatus] = useState<FormStatus | null>(null);

  const { data: eventos = [], isLoading } = useQuery({
    queryKey: ["eventos"],
    queryFn: () => eventoService.listarTodos(),
  });

  const createMutation = useMutation({
    mutationFn: (dados: EventoRequest) => eventoService.criar(dados),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["eventos"] });
      setFormStatus({ type: "success", message: "Evento criado com sucesso!" });
      setTimeout(() => setFormStatus(null), 3000);
    },
    onError: (error: any) => {
      const message =
        error.response?.data?.message ||
        "Não foi possível criar o evento. Verifique seus dados.";
      setFormStatus({ type: "error", message });
    },
  });

  const deleteMutation = useMutation({
    mutationFn: (id: number) => eventoService.remover(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["eventos"] });
    },
  });

  const meusEventos = eventos.filter(
    (ev) => ev.organizadorId === user?.userId,
  );

  const handleSubmit = async (
    values: EventoRequest,
    helpers: FormikHelpers<EventoRequest>,
  ) => {
    createMutation.mutate(values);
    helpers.resetForm();
  };

  return (
    <Page>
      {/* Formulário de criação */}
      <Section>
        <SectionTitle>Criar novo evento</SectionTitle>
        <Formik
          initialValues={{
            titulo: "",
            descricao: "",
            data: "",
            local: "",
            vagas: 1,
            imagemUrl: "",
          }}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          {({ isSubmitting }) => (
            <StyledForm noValidate>
              <Label>
                Título
                <Input
                  name="titulo"
                  type="text"
                  placeholder="Nome do evento"
                />
                <ErrorText name="titulo" component="span" />
              </Label>

              <Label>
                Descrição
                <Textarea
                  name="descricao"
                  placeholder="Descreva o que será o evento..."
                />
                <ErrorText name="descricao" component="span" />
              </Label>

              <Label>
                Data e Hora
                <Input name="data" type="datetime-local" />
                <ErrorText name="data" component="span" />
              </Label>

              <Label>
                Local
                <Input
                  name="local"
                  type="text"
                  placeholder="Endereço ou link"
                />
                <ErrorText name="local" component="span" />
              </Label>

              <Label>
                Vagas
                <Input
                  name="vagas"
                  type="number"
                  placeholder="Número de vagas"
                  min={1}
                />
                <ErrorText name="vagas" component="span" />
              </Label>

              <Label>
                URL da Imagem (opcional)
                <Input
                  name="imagemUrl"
                  type="url"
                  placeholder="https://exemplo.com/imagem.jpg"
                />
                <ErrorText name="imagemUrl" component="span" />
              </Label>

              {formStatus && (
                <StatusMessage $status={formStatus.type}>
                  {formStatus.message}
                </StatusMessage>
              )}

              <SubmitButton
                disabled={isSubmitting || createMutation.isPending}
                type="submit"
              >
                {createMutation.isPending ? "Criando..." : "Criar Evento"}
              </SubmitButton>
            </StyledForm>
          )}
        </Formik>
      </Section>

      {/* Lista dos meus eventos */}
      <Section>
        <SectionTitle>Meus Eventos</SectionTitle>
        {isLoading ? (
          <Placeholder>Carregando eventos...</Placeholder>
        ) : meusEventos.length === 0 ? (
          <Placeholder>
            Você ainda não criou nenhum evento. Use o formulário acima para
            começar.
          </Placeholder>
        ) : (
          <EventList>
            {meusEventos.map((evento) => (
              <EventCard key={evento.id}>
                <EventInfo>
                  <EventName>{evento.titulo}</EventName>
                  <EventMeta>
                    {new Date(evento.data).toLocaleDateString("pt-BR")} —{" "}
                    {evento.local} — {evento.vagas} vagas
                  </EventMeta>
                </EventInfo>
                <EventActions>
                  <IconBtn onClick={() => navigate(`/evento/${evento.id}`)}>
                    Ver
                  </IconBtn>
                  <IconBtn
                    $variant="danger"
                    onClick={() =>
                      deleteMutation.mutate(evento.id)
                    }
                    disabled={deleteMutation.isPending}
                  >
                    Remover
                  </IconBtn>
                </EventActions>
              </EventCard>
            ))}
          </EventList>
        )}
      </Section>
    </Page>
  );
};

export default Admin;
